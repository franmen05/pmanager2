package com.pmanager.service.impl;

import com.pmanager.domain.RecordItem;
import com.pmanager.repository.RecordItemRepository;
import com.pmanager.service.RecordItemService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link RecordItem}.
 */
@Service
@Transactional
public class RecordItemServiceImpl implements RecordItemService {
    private final Logger log = LoggerFactory.getLogger(RecordItemServiceImpl.class);

    private final RecordItemRepository recordItemRepository;

    public RecordItemServiceImpl(RecordItemRepository recordItemRepository) {
        this.recordItemRepository = recordItemRepository;
    }

    @Override
    public RecordItem save(RecordItem recordItem) {
        log.debug("Request to save RecordItem : {}", recordItem);
        return recordItemRepository.save(recordItem);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RecordItem> findAll() {
        log.debug("Request to get all RecordItems");
        return recordItemRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RecordItem> findOne(Long id) {
        log.debug("Request to get RecordItem : {}", id);
        return recordItemRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete RecordItem : {}", id);
        recordItemRepository.deleteById(id);
    }
}
