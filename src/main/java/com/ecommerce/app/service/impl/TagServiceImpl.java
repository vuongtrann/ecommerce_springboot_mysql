package com.ecommerce.app.service.impl;

import com.ecommerce.app.exception.AppException;
import com.ecommerce.app.model.dao.request.TagForm;
import com.ecommerce.app.model.dao.response.projection.TagProjection;
import com.ecommerce.app.model.entity.Collection;
import com.ecommerce.app.model.entity.Product;
import com.ecommerce.app.model.entity.Tag;
import com.ecommerce.app.repository.ProductRepository;
import com.ecommerce.app.repository.TagRepository;
import com.ecommerce.app.service.TagService;
import com.ecommerce.app.utils.ErrorCode;
import com.ecommerce.app.utils.Status;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TagServiceImpl implements TagService {

    TagRepository tagRepository;
    private final ProductRepository productRepository;

    @Override
    public Page<TagProjection> findAll(int page, int size, String sortBy, String direction) {
        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        return tagRepository.findAllProjectedBy(pageable);
    }

    @Override
    public Optional<Tag> findById(String id) {
        return tagRepository.findById(id);
    }

    @Override
    public List<Tag> findByIdIn(List<String> ids) {
        return tagRepository.findAllByIdIn(ids);
    }

    @Override
    public Tag create(TagForm form) {
        boolean existsTag = tagRepository.existsByTagName(form.getTagName());
        if(existsTag) {
            throw new AppException(ErrorCode.TAG_ALREADY_EXISTS);
        }
        Tag tag = new Tag(form.getTagName());
        return save(tag);
    }

    @Override
    public Tag update(String id, TagForm form) {
        Tag tag = tagRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.TAG_NOT_FOUND));
        boolean existsTag = tagRepository.existsByTagName(form.getTagName());
        if(existsTag) {
            throw new AppException(ErrorCode.TAG_ALREADY_EXISTS);
        }
        tag.setTagName(form.getTagName());
        return save(tag);

    }

    @Override
    public Tag save(Tag tag) {
        return tagRepository.save(tag);
    }

    @Override
    public void delete(String id) {
        Tag tag = tagRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.TAG_NOT_FOUND));
        if (tag.getStatus().equals("ACTIVE")) {
            throw new AppException(ErrorCode.TAG_STATUS_IS_ACTIVE);
        }
        /**TODO
         * Cần tạo thêm 1 cronjob ở đây để xóa tag này ra khỏi những sản phẩm đã có*/
        tagRepository.delete(tag);
    }

    @Override
    public void changeStatus(String id) {
        Tag tag = tagRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.TAG_NOT_FOUND));
        if (tag.getStatus().equals("ACTIVE")) {
            tag.setStatus(Status.INACTIVE);
        } else {
            tag.setStatus(Status.ACTIVE);
        }
        tagRepository.save(tag);
    }
    @Override
    public void addTagToProduct(String productId, String tagId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));

        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new AppException(ErrorCode.TAG_NOT_FOUND));

        if (!product.getTags().contains(tag)) {
            product.getTags().add(tag);
            productRepository.save(product);
        }
    }
}
