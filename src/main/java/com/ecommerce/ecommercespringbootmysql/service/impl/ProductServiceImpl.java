package com.ecommerce.ecommercespringbootmysql.service.impl;

import com.ecommerce.ecommercespringbootmysql.exception.AppException;
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

import java.util.List;
import java.util.Optional;

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
    public Product create(Product product) {
        return null;
    }

    @Override
    public Product update(Product product) {
        return null;
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
