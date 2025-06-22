package com.ecommerce.app.repository;

import com.ecommerce.app.model.dao.response.projection.CommentProjection;
import com.ecommerce.app.model.entity.Comment;
import com.ecommerce.app.model.entity.User;
import com.ecommerce.app.utils.Enum.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface CommentRepository  extends JpaRepository<Comment, String> {

    @Query("SELECT c FROM Comment c WHERE c.user.UID = ?1 AND c.status = ?2")
    List<Comment> findByUIDAndStatus(Long uid, Status status);
    Long user(User user);

    @Query("SELECT c.content AS content, u.UID AS uid, p.id AS productId " +
            "FROM Comment c " +
            "JOIN c.user u " +
            "JOIN c.product p")
    Page<CommentProjection> findAllCommentsWithUserAndProduct(Pageable pageable);

    List<Comment> findByProduct_Id(String productId);
}
