package com.ecommerce.ecommercespringbootmysql.service.impl;

import com.ecommerce.ecommercespringbootmysql.exception.AppException;
import com.ecommerce.ecommercespringbootmysql.model.dao.request.ProductForm;
import com.ecommerce.ecommercespringbootmysql.model.entity.Category;
import com.ecommerce.ecommercespringbootmysql.model.entity.Product;
import com.ecommerce.ecommercespringbootmysql.repository.ProductRepository;
import com.ecommerce.ecommercespringbootmysql.service.CategoryService;
import com.ecommerce.ecommercespringbootmysql.service.ProductSerice;
import com.ecommerce.ecommercespringbootmysql.service.utils.SlugifyService;
import com.ecommerce.ecommercespringbootmysql.utils.ErrorCode;
import com.ecommerce.ecommercespringbootmysql.utils.Status;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductServiceImpl implements ProductSerice {

    ProductRepository productRepository;
    CategoryService categoryService;
    SlugifyService slugify;


    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product create(ProductForm form) {
        List<Category> categories = categoryService.findByIdIn(form.getCategories());

        Product product = new Product(
                form.getName(),
                form.getDescription(),
                slugify.generateSlug(form.getName()+ "-" + Instant.now().getEpochSecond()),
                form.getSku(),
                form.getQuantity(),
                form.getOriginalPrice(),
                form.getSellingPrice(),
                form.getDiscountedPrice(),
                form.getSellingType(),
                categories
        );
        /***TODO
         * Cần check xem sản phẩm đó không trùng tên
         * Cần check xem sản phẩm đó không trùng sku
         * Cần check xem sản phẩm đó không trùng slug
         * Cần xử lý variant nếu có
         * Cần xử lý spectification nếu có
         * Cần xử lý thêm người tạo nếu có
         * */

        product.setQuantityAvailable(form.getQuantity());

        product.setStatus(Status.ACTIVE);
        product.setCreatedAt(Instant.now().toEpochMilli());
        product.setUpdatedAt(Instant.now().toEpochMilli());

        Product savedProduct = save(product);
        return savedProduct;
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
        if (!product.getStatus().equals("ACTIVE")) {
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
}
