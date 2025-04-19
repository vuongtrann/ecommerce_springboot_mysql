package com.ecommerce.app.repository;

import com.ecommerce.app.model.entity.UidSequence;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UidSequenceRepository extends JpaRepository<UidSequence, Long> {

}
