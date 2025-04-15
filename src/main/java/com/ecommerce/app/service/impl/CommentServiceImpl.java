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
import com.ecommerce.app.utils.Role;
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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;

    private final UserRepositiory userRepository;

    private final ProductRepository productRepository;


    @Override
    public void createComment(CommentForm form, Long uid, String productId) {

        if (uid == null) {
            throw new RuntimeException("Bạn phải đăng nhập để comment.");
        }


        User user = userRepository.findByUid(uid)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng."));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm."));

        Comment comment = CommentMapper.toEntity(form, user, product);
        commentRepository.save(comment);
    }

    @Override
    public List<CommentResponse> getCommentsByUserId(Long uid) {
        User user = userRepository.findByUid(uid)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng."));
        List<Comment> comments = commentRepository.findByUser_IdAndStatus(user.getId(), Status.ACTIVE);
        return CommentMapper.toResponseList(comments);
    }

    @Override
    public Page<CommentResponse> getAllComments(int page, int size, String sortBy, String direction) {
        Sort sort = direction.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Comment> comments = commentRepository.findAll(pageable);
        return CommentMapper.toResponsePage(comments);
    }

    @Override
    public void hideComment(String commentId, Long userUid) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy comment."));

        User currentUser = userRepository.findByUid(userUid)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng."));

        boolean isOwner = comment.getUser().getUid().equals(userUid);
        boolean isAdmin = currentUser.getRole().equals(Role.ADMIN);

        if (!isOwner && !isAdmin) {
            throw new RuntimeException("Bạn không có quyền xoá comment này.");
        }

        comment.setStatus(Status.INACTIVE);
        commentRepository.save(comment);

    }

    @Override
    public void deleteComment(String commentId, Long userUid) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy comment."));

        User currentUser = userRepository.findByUid(userUid)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng."));

        boolean isOwner = comment.getUser().getUid().equals(userUid);
        boolean isAdmin = currentUser.getRole().equals(Role.ADMIN);

        if (!isOwner && !isAdmin) {
            throw new RuntimeException("Bạn không có quyền xoá comment này.");
        }
        commentRepository.delete(comment);
    }

    @Override
    public void updateComment(String commentId, Long userUid, CommentForm form) {
        // Tìm user theo UID
        User user = userRepository.findByUid(userUid)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy user."));

        // Tìm comment theo ID và check trạng thái ACTIVE
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy comment."));

        if (!comment.getStatus().equals(Status.ACTIVE)) {
            throw new RuntimeException("Comment này bị ẩn hoặc xóa.");
        }

        // Chỉ người tạo mới được sửa comment
        if (!comment.getUser().getId().equals(user.getId())) {
            throw  new RuntimeException("Bạn không có quyền chỉnh sửa comment này");
        }

        // Cập nhật nội dung
        comment.setContent(form.getContent());
        comment.setUpdatedAt(System.currentTimeMillis());
        commentRepository.save(comment);
    }

}
