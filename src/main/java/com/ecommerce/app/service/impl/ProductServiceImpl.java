package com.ecommerce.app.service.impl;

import com.ecommerce.app.exception.AppException;
import com.ecommerce.app.model.dao.request.ProductForm;
import com.ecommerce.app.model.dao.request.Variant.ProductVariantForm;
import com.ecommerce.app.model.dao.request.Variant.VariantOptionForm;
import com.ecommerce.app.model.dao.response.dto.ProductResponse;
import com.ecommerce.app.model.dao.response.projection.ProductProjection;
import com.ecommerce.app.model.entity.*;
import com.ecommerce.app.model.entity.Collection;
import com.ecommerce.app.model.entity.Variant.ProductVariant;
import com.ecommerce.app.model.entity.Variant.VariantOption;
import com.ecommerce.app.model.entity.Variant.VariantType;
import com.ecommerce.app.model.mapper.ProductMapper;
import com.ecommerce.app.repository.*;
import com.ecommerce.app.service.*;
import com.ecommerce.app.service.utils.SlugifyService;
import com.ecommerce.app.utils.ErrorCode;
import com.ecommerce.app.utils.Status;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductServiceImpl implements ProductSerice {

    ProductRepository productRepository;
    CloudinaryService cloudinaryService;
    ImageRepository imageRepository;

    CategoryService categoryService;
    BrandService brandService;
    CollectionService collectionService;
    TagService tagService;
    SlugifyService slugify;


    VariantTypeRepository variantTypeRepository;
    VariantOptionRepository variantOptionRepository;
    ProductVariantRepository productVariantRepository;


    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Page<ProductProjection> findAll(int page, int size, String sortBy, String direction) {
        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        return productRepository.findAllProjectedBy(pageable);
    }


    @Override
    public List<String> uploadImagesToProduct(String productId, List<MultipartFile> files) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        String folderName = "ecommerce/products/" + productId;
        List<String> uploadedUrls = cloudinaryService.uploadImages(files, folderName);

        if (!uploadedUrls.isEmpty()) {
            product.setPrimaryImageURL(uploadedUrls.get(0)); // ảnh đầu tiên
        }

        List<Image> imageEntities = new ArrayList<>();
        for (String url : uploadedUrls) {
            Image img = new Image();
            img.setUrl(url);
            img.setProduct(product);
            imageEntities.add(img);
        }

        imageRepository.saveAll(imageEntities);
        productRepository.save(product);

        return uploadedUrls;
    }

    @Override
    public void removeImagesFromProduct(String productId) {
        if (!productRepository.existsById(productId)) {
            throw new RuntimeException("Product not found");
        }
        imageRepository.deleteByProductId(productId);
    }


    @Override
    public List<String> uploadImagesToVariant(String variantId, String productId, List<MultipartFile> files) {

        ProductVariant variant = productVariantRepository.findById(variantId)
                .orElseThrow(() -> new RuntimeException("ProductVariant not found"));

        String folderName = "ecommerce/products/" + productId + "/variant/" + variantId;

        List<String> urls = cloudinaryService.uploadImages(files, folderName);


        List<Image> images = urls.stream().map(url -> {
            Image img = new Image();
            img.setUrl(url);
            img.setProductVariant(variant);
            return img;
        }).collect(Collectors.toList());

        imageRepository.saveAll(images);

        return urls;
    }

    @Override
    public Product create(ProductForm form) {
        List<Category> categories = categoryService.findByIdIn(form.getCategories());
        if (Objects.isNull(categories) || categories.isEmpty()) {
            throw new AppException(ErrorCode.CATEGORY_NOT_FOUND);
        }

        List<Brand> brands = brandService.findByIdIn(form.getBrands());

        List<Collection> collections = collectionService.findByIdIn(form.getCollections());

        List<Tag> tags = tagService.findByIdIn(form.getTags());

        Product product = ProductMapper.toEntity(form, categories, brands, collections, tags);

        product.setQuantityAvailable(form.getQuantity());
        product.setStatus(Status.ACTIVE);
        product.setCreatedAt(Instant.now().toEpochMilli());
        product.setUpdatedAt(Instant.now().toEpochMilli());

        product = productRepository.save(product); // 🔹 Lưu product vào DB trước

        // 👉 Bước 2: Tạo và lưu ProductVariant sau khi Product đã có ID
        if (form.isHasVariants()) {
            List<ProductVariant> variants = new ArrayList<>();
            for (ProductVariantForm variantForm : form.getVariants()) {
                ProductVariant productVariant = createProductVariant(variantForm, product);
                variants.add(productVariantRepository.save(productVariant)); // 🔹 Lưu ProductVariant trước
            }
            // 🔹 Cập nhật danh sách variants mà không thay thế toàn bộ danh sách
            if (product.getVariants() == null) {
                product.setVariants(new ArrayList<>());
            }
            product.getVariants().clear();
            product.getVariants().addAll(variants);
            product.setHasVariants(true);
        }

        // 👉 Bước 3: Cập nhật lại Product sau khi thêm variants
        Product saved = productRepository.save(product);

        return saved;
    }

    @Override
    public Product update(String id ,ProductForm form) {
        Product product = productRepository.findById(id).orElseThrow(()-> new AppException(ErrorCode.PRODUCT_NOT_FOUND));
        List<Category> categories = categoryService.findByIdIn(form.getCategories());

        product.setName(form.getName());
        product.setDescription(form.getDescription());
        product.setSlug(slugify.generateSlug(form.getName()+ "-" + Instant.now().getEpochSecond()));
        product.setSku(form.getSku());
        product.setQuantity(form.getQuantity());
        product.setOriginalPrice(form.getOriginalPrice());
        product.setSellingPrice(form.getSellingPrice());
        product.setDiscountedPrice(form.getDiscountedPrice());
        product.setSellingType(form.getSellingType());
        product.setCategories(categories);
        product.setQuantityAvailable(form.getQuantity() - product.getSoldQuantity());


        /***TODO
         * Cần check xem sản phẩm đó không trùng tên
         * Cần check xem sản phẩm đó không trùng sku
         * Cần check xem sản phẩm đó không trùng slug
         * Cần xử lý variant nếu có
         * Cần xử lý spectification nếu có
         * cần xử lý người cập nhật nếu có
         * */

        product.setUpdatedAt(Instant.now().toEpochMilli());

        Product updatedProduct = save(product);
        return updatedProduct;

    }

    @Override
    public Product uploadImage(String id, List<MultipartFile> files) {
        return null;
    }

    @Override
    public void delete(String id) {
        Product product = productRepository.findById(id).orElseThrow(()-> new AppException(ErrorCode.PRODUCT_NOT_FOUND));
        if (product.getStatus().equals("ACTIVE")) {
            throw new AppException(ErrorCode.PRODUCT_CANNOT_DELETE);
        }
        /**TODO
         * Cần check thêm sản phẩm đó đang được mua hay ko, sản phẩm đó có đang nằm trong top hay ko, check các case có thể xảy ra
         **/
        product.setStatus(Status.DELETED);
        productRepository.save(product);
    }

    @Override
    public Optional<Product> findById(String id) {
        Product product = productRepository.findById(id).orElseThrow(()-> new AppException(ErrorCode.PRODUCT_NOT_FOUND));
        /***TODO
         * Cần caching lại khi gọi findByID để sử dụng cho việc sản phẩm nổi bật, sản phẩm vừa xem
         * */
        return Optional.of(product);
    }

    @Override
    public ProductResponse getProductById(String id) {
        Product product = productRepository.findById(id).orElseThrow(()-> new AppException(ErrorCode.PRODUCT_NOT_FOUND));
        ProductResponse productResponse = ProductMapper.toResponse(product);
        return productResponse;
    }

    @Override
    public Optional<Product> findBySlug(String slug) {
        Product product = productRepository.findProductBySlug(slug).orElseThrow(()-> new AppException(ErrorCode.PRODUCT_NOT_FOUND));
        /***TODO
         * Cần caching lại khi gọi findByID để sử dụng cho việc sản phẩm nổi bật, sản phẩm vừa xem
         * */
        return Optional.of(product);
    }

    @Override
    public void changeStatus(String id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));
        if (product.getStatus() == Status.ACTIVE) {
            product.setStatus(Status.INACTIVE);
        } else {
            product.setStatus(Status.ACTIVE);
        }
        productRepository.save(product);
    }

    @Override
    public List<VariantType> getVariantTypes() {
        return variantTypeRepository.findAll();
    }

    @Override
    public Optional<VariantType> getVariantType(Long id) {
        return variantTypeRepository.findById(id);
    }

    @Override
    public VariantType createVariantType(VariantType variantType) {
        VariantType savedVariantType = new VariantType();
        savedVariantType.setType(variantType.getType().toUpperCase());
        return variantTypeRepository.save(savedVariantType);
    }

    @Override
    public VariantType updateVariantType(String id, VariantType variantType) {
        return null;
    }


    private ProductVariant createProductVariant(ProductVariantForm variantForm, Product product) {
        ProductVariant productVariant = new ProductVariant();
        productVariant.setSku(variantForm.getSku());
        productVariant.setQuantityAvailable(variantForm.getQuantityAvailable());
        productVariant.setOriginalPrice(variantForm.getOriginalPrice());
        productVariant.setSellingPrice(variantForm.getSellingPrice());
        productVariant.setDiscountedPrice(variantForm.getDiscountedPrice());

        productVariant.setProduct(product);

        // 🔹 Lưu ProductVariant trước khi tạo VariantOptions
        productVariant = productVariantRepository.save(productVariant);

        List<VariantOption> variantOptions = new ArrayList<>();
        for (VariantOptionForm optionForm : variantForm.getVariantOptions()) {
            VariantType variantType = getVariantType(optionForm.getVariantTypeId()).orElseThrow(()-> new AppException(ErrorCode.VARIANT_TYPE_NOT_FOUND));
            VariantOption variantOption = new VariantOption();
            variantOption.setValue(optionForm.getValue());
            variantOption.setVariantType(variantType);
            variantOption.setProductVariant(productVariant);


            variantOption = variantOptionRepository.save(variantOption); // 🔹 Lưu VariantOption vào DB trước
            variantOptions.add(variantOption);
        }

        productVariant.getVariantOptions().clear();
        productVariant.getVariantOptions().addAll(variantOptions);

        return productVariantRepository.save(productVariant); // Cập nhật lại ProductVariant với danh sách VariantOptions
    }
}
