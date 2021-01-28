package com.pmanager.repository;

import com.pmanager.domain.MedicalHistory;
import com.pmanager.domain.RecordItem;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MedicalHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MedicalHistoryRepository extends JpaRepository<MedicalHistory, Long> {
    List<MedicalHistory> findByRecordId(Long var1);
}
