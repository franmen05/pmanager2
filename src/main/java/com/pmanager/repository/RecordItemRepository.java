package com.pmanager.repository;

import com.pmanager.domain.RecordItem;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the RecordItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RecordItemRepository extends JpaRepository<RecordItem, Long> {}
