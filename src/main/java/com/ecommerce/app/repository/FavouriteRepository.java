package com.ecommerce.app.repository;

import com.ecommerce.app.model.entity.Favourite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavouriteRepository extends JpaRepository<Favourite, String> {
    List<Favourite> findByUser_UID(Long userUid);
    Optional<Favourite> findByUser_UIDAndProduct_Id(Long userUid, String productId);
}
