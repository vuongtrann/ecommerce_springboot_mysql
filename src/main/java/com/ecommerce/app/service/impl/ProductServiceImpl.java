package com.ecommerce.app.service.impl;

import com.ecommerce.app.exception.AppException;
import com.ecommerce.app.model.dao.request.ProductForm;
import com.ecommerce.app.model.dao.request.Variant.ProductVariantForm;
import com.ecommerce.app.model.dao.request.Variant.VariantOptionForm;
import com.ecommerce.app.model.dao.response.dto.ProductResponse;
import com.ecommerce.app.model.dao.response.projection.ProductProjection;
import com.ecommerce.app.model.dao.response.projection.ProductWithAvgRatingProjection;
import com.ecommerce.app.model.entity.*;
import com.ecommerce.app.model.entity.Collection;
import com.ecommerce.app.model.entity.Variant.ProductVariant;
import com.ecommerce.app.model.entity.Variant.VariantOption;
import com.ecommerce.app.model.entity.Variant.VariantType;
import com.ecommerce.app.model.mapper.ProductMapper;
import com.ecommerce.app.repository.*;
import com.ecommerce.app.service.*;
import com.ecommerce.app.service.utils.SlugifyService;
import com.ecommerce.app.utils.Enum.ErrorCode;
import com.ecommerce.app.utils.Enum.Status;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductServiceImpl implements ProductSerice {

    ProductRepository productRepository;
    CloudinaryService cloudinaryService;
    ImageRepository imageRepository;
    ProductMapper productMapper;
    ProductVariantRepository productVariant;
    CategoryRepository categoryRepository;
    BrandRepository brandRepository;
    CollectionRepository collectionRepository;
    TagRepository tagRepository;

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
    public List<ProductResponse> search(String keyword){
        if(keyword == null || keyword.isEmpty()){
            return Collections.emptyList();
        }
        List<Product> products = productRepository.searchProductByNameOrSlug(keyword);

        return products.stream().map(
                ProductMapper::toResponse
        ).collect(Collectors.toList());
    }

    @Override
    public Page<ProductResponse> getTopViewedProducts(int page, int size, String direction) {
        Sort sort = Sort.by(Sort.Direction.fromString(direction), "noOfView");
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Product> products = productRepository.findAll(pageable);
        return products.map(ProductMapper::toSimpleResponse);
    }

    @Override
    public Page<ProductResponse> getTopRatedProducts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<ProductWithAvgRatingProjection> projections = productRepository.findAllWithAvgRating(pageable);

        return projections.map(product -> {
            ProductResponse response = new ProductResponse();
            response.setId(product.getProductId());
            response.setName(product.getProductName());
            response.setDescription(product.getProductDescription());
            response.setPrimaryImageURL(product.getPrimaryImageUrl());
            response.setSellingPrice(product.getSellingPrice());
            response.setAvgRating(product.getAvgRating());
            return response;
        });
    }





    @Override
    public List<String> uploadImagesToProduct(String productId, List<MultipartFile> files) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() ->new AppException(ErrorCode.PRODUCT_NOT_FOUND));

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
            throw new AppException(ErrorCode.PRODUCT_NOT_FOUND);
        }
        imageRepository.deleteByProductId(productId);
    }


    @Override
    public List<String> uploadImagesToVariant(String variantId, String productId, List<MultipartFile> files) {

        ProductVariant variant = productVariantRepository.findById(variantId)
                .orElseThrow(() ->new AppException(ErrorCode.PRODUCT_VARIANT_NOT_FOUND));

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

        String slug = slugify.generateSlug(form.getName());
        product.setSlug(slug);
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
//    @Caching(put = {
//            @CachePut (value = "PRODUCT_BY_ID", key = "#productId"),
//            @CachePut (value = "PRODUCT_BY_SLUG", key ="#result.slug")
//    })
    public ProductResponse update(String productId ,ProductForm form) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() ->  new AppException(ErrorCode.PRODUCT_NOT_FOUND
                ));

        // Check duplicate name
        if (!product.getName().equalsIgnoreCase(form.getName())) {
            if (productRepository.existsByName(form.getName())) {
                throw new AppException(ErrorCode.PRODUCT_NAME_ALREADY_EXISTS);
            }
            product.setName(form.getName());

            // Update slug if name changed
            String newSlug = slugify.generateSlug(form.getName());
            if (productRepository.existsBySlug(newSlug)) {
                newSlug += "-" + UUID.randomUUID().toString().substring(0, 8);
            }
            product.setSlug(newSlug);
        }

        product.setDescription(form.getDescription());
        product.setPrimaryImageURL(form.getPrimaryImageURL());
        product.setSku(form.getSku());
        product.setOriginalPrice(form.getOriginalPrice());
        product.setSellingPrice(form.getSellingPrice());
        product.setDiscountedPrice(form.getDiscountedPrice());
        product.setSellingType(form.getSellingType());
        product.setQuantity(form.getQuantity());

        // Handle variants
        boolean hasVariants = form.isHasVariants();
        if (hasVariants) {
            product.getVariants().clear(); //  Hibernate xử lý orphan
            List<ProductVariant> newVariants = form.getVariants().stream()
                    .map(variantForm -> productMapper.toVariantEntity(variantForm, product))
                    .collect(Collectors.toList());
            product.getVariants().addAll(newVariants);
        } else {
            product.getVariants().clear(); // Không có variant thì clear
        }


        // Handle categories
        List<Category> updatedCategories = categoryRepository.findAllByIdIn(form.getCategories());
        product.setCategories(updatedCategories);

        // Handle tags
//        List<Tag> updatedTags = tagRepository.findAllByIdIn(form.getTags());
//        product.setTags(updatedTags);

        // Handle brands
        List<Brand> updatedBrands = brandRepository.findAllByIdIn(form.getBrands());
        product.setBrands(updatedBrands);

        // Handle collections
        List<Collection> updatedCollections = collectionRepository.findAllByIdIn(form.getCollections());
        product.setCollections(updatedCollections);

        Product savedProduct = productRepository.save(product);
        return ProductMapper.toResponse(savedProduct);
//        return productMapper.toResponse(savedProduct);

    }

    @Override
    public Product uploadImage(String id, List<MultipartFile> files) {
        return null;
    }

    @Override
    //Clear cache when product is deleted
    @Caching(evict = {
            @CacheEvict(value = "PRODUCT_BY_ID", key = "#id")

    })
    public void delete(String id) {
        Product product = productRepository.findById(id).orElseThrow(()-> new AppException(ErrorCode.PRODUCT_NOT_FOUND));
        if (product.getStatus().equals("ACTIVE")) {
            throw new AppException(ErrorCode.PRODUCT_CANNOT_DELETE);
        }
        /**TODO
         * Cần check thêm sản phẩm đó đang được mua hay ko, sản phẩm đó có đang nằm trong top hay ko, check các case có thể xảy ra
         **/
//        productRepository.delete(product);
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
    //Cache product by id
//    @Cacheable(value = "PRODUCT_BY_ID", key = "#id")
    public ProductResponse getProductById(String id) {
        Product product = productRepository.findById(id).orElseThrow(()-> new AppException(ErrorCode.PRODUCT_NOT_FOUND));

        product.setNoOfView(product.getNoOfView() + 1);
        productRepository.save(product); // lưu lại lượt xem tăng
        ProductResponse productResponse = ProductMapper.toResponse(product);
        return productResponse;
    }

    @Override
    //Cache product by slug
    @Cacheable(value = "PRODUCT_BY_SLUG", key = "#slug")
    public ProductResponse findBySlug(String slug) {
        Product product = productRepository.findProductBySlug(slug).orElseThrow(()-> new AppException(ErrorCode.PRODUCT_NOT_FOUND));
        product.setNoOfView(product.getNoOfView() + 1);
        productRepository.save(product); // lưu lại lượt xem tăng

        ProductResponse productResponse = ProductMapper.toSimpleResponse(product);
        return productResponse;
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

    @Override
    @Transactional
    public ProductResponse getProductDetail(String slug) {
        Product product = productRepository.findBySlug(slug)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));

        product.setNoOfView(product.getNoOfView() + 1);
        productRepository.save(product); // lưu lại lượt xem tăng

        ProductResponse productResponse = ProductMapper.toSimpleResponse(product);
        return productResponse;
    }





    @Override
    public Page<ProductResponse> getNewestProducts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Product> productPage = productRepository.findAllByOrderByCreatedAtDesc(pageable);
        return productPage.map(ProductMapper::toSimpleResponse);
    }
}
