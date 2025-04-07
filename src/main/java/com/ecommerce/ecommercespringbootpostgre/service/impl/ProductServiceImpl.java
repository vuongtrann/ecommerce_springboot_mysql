package com.ecommerce.ecommercespringbootpostgre.service.impl;

import com.ecommerce.ecommercespringbootpostgre.exception.AppException;
import com.ecommerce.ecommercespringbootpostgre.model.dao.request.ProductForm;
import com.ecommerce.ecommercespringbootpostgre.model.dao.request.Variant.ProductVariantForm;
import com.ecommerce.ecommercespringbootpostgre.model.dao.request.Variant.VariantOptionForm;
import com.ecommerce.ecommercespringbootpostgre.model.dao.response.projection.ProductProjection;
import com.ecommerce.ecommercespringbootpostgre.model.entity.Category;
import com.ecommerce.ecommercespringbootpostgre.model.entity.Product;
import com.ecommerce.ecommercespringbootpostgre.model.entity.Variant.ProductVariant;
import com.ecommerce.ecommercespringbootpostgre.model.entity.Variant.VariantOption;
import com.ecommerce.ecommercespringbootpostgre.model.entity.Variant.VariantType;
import com.ecommerce.ecommercespringbootpostgre.repository.*;
import com.ecommerce.ecommercespringbootpostgre.service.CategoryService;
import com.ecommerce.ecommercespringbootpostgre.service.ProductSerice;
import com.ecommerce.ecommercespringbootpostgre.service.utils.SlugifyService;
import com.ecommerce.ecommercespringbootpostgre.utils.ErrorCode;
import com.ecommerce.ecommercespringbootpostgre.utils.Status;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductServiceImpl implements ProductSerice {

    ProductRepository productRepository;
    CategoryService categoryService;
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
    public Product create(ProductForm form) {
        List<Category> categories = categoryService.findByIdIn(form.getCategories());
        if (Objects.isNull(categories) || categories.isEmpty()) {
            throw new AppException(ErrorCode.CATEGORY_NOT_FOUND);
        }


        // 👉 Bước 1: Lưu Product trước để có ID
        Product product = new Product(
                form.getName(),
                form.getDescription(),
                slugify.generateSlug(form.getName() + "-" + Instant.now().getEpochSecond()),
                form.getSku(),
                form.getQuantity(),
                form.getOriginalPrice(),
                form.getSellingPrice(),
                form.getDiscountedPrice(),
                form.getSellingType(),
                categories
        );


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
            product.getVariants().clear();
            product.getVariants().addAll(variants);        }

        // 👉 Bước 3: Cập nhật lại Product sau khi thêm variants
        return productRepository.save(product);
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


            variantOption = variantOptionRepository.save(variantOption); // 🔹 Lưu VariantOption vào DB trước
            variantOptions.add(variantOption);
        }

        productVariant.getVariantOptions().clear();
        productVariant.getVariantOptions().addAll(variantOptions);

        return productVariantRepository.save(productVariant); // Cập nhật lại ProductVariant với danh sách VariantOptions
    }
}
