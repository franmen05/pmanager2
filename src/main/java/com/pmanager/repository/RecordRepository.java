package com.pmanager.repository;

import com.pmanager.domain.Patient;
import com.pmanager.domain.Record;
import com.pmanager.domain.Turn;
import java.time.Instant;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Record entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RecordRepository extends JpaRepository<Record, Long> {
    List<Record> findAllByPatientOrderByCreateDateDesc(Patient id);
}
