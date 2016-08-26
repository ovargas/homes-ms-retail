package com.tenx.ms.retail.repositories;

import com.tenx.ms.retail.domain.StoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StoreRepository extends JpaRepository<StoreEntity, Long> {

    Optional<StoreEntity> findById(Long id);
}
