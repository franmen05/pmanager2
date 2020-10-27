package com.pmanager.repository;

import com.pmanager.domain.Turn;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Turn entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TurnRepository extends JpaRepository<Turn, Long> {
    List<Turn> findAllByCreateDateAfter(Instant date);
}
