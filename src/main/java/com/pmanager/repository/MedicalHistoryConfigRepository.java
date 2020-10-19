package com.pmanager.repository;

import com.pmanager.domain.MedicalHistoryConfig;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MedicalHistoryConfig entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MedicalHistoryConfigRepository extends JpaRepository<MedicalHistoryConfig, Long> {
}
