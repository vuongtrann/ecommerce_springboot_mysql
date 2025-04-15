package com.ecommerce.app.repository;

import com.ecommerce.app.model.entity.Comment;
import com.ecommerce.app.utils.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface CommentRepository  extends JpaRepository<Comment, String> {
    List<Comment> findByUser_IdAndStatus(Long userId,  Status status);
}
