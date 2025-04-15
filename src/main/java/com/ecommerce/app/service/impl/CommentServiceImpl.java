package com.ecommerce.app.service.impl;

import com.ecommerce.app.exception.AppException;
import com.ecommerce.app.model.dao.request.CommentForm;
import com.ecommerce.app.model.dao.response.dto.CommentResponse;
import com.ecommerce.app.model.dao.response.dto.ProductResponse;
import com.ecommerce.app.model.entity.Comment;
import com.ecommerce.app.model.entity.Product;
import com.ecommerce.app.model.entity.User;
import com.ecommerce.app.model.mapper.CommentMapper;
import com.ecommerce.app.model.mapper.ProductMapper;
import com.ecommerce.app.repository.CommentRepository;
import com.ecommerce.app.repository.ProductRepository;
import com.ecommerce.app.repository.UserRepositiory;
import com.ecommerce.app.service.CommentService;
import com.ecommerce.app.utils.ErrorCode;
import com.ecommerce.app.utils.Status;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;

    private final UserRepositiory userRepository;

    private final ProductRepository productRepository;


    @Override
    public void createComment(CommentForm form, Long userId, String productId) {

        if (userId == null || (userId + "").isBlank()) {
            throw new RuntimeException("Bạn phải đăng nhập để comment.");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng."));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm."));

        Comment comment = CommentMapper.toEntity(form, user, product);
        commentRepository.save(comment);
    }

    @Override
    public List<CommentResponse> getCommentsByUserId(Long userId) {
        List<Comment> comments = commentRepository.findByUser_IdAndStatus(userId, Status.ACTIVE);
        return CommentMapper.toResponseList(comments);
    }

}
