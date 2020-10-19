package com.pmanager.service.impl;

import com.pmanager.service.RecordService;
import com.pmanager.domain.Record;
import com.pmanager.repository.RecordRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Record}.
 */
@Service
@Transactional
public class RecordServiceImpl implements RecordService {

    private final Logger log = LoggerFactory.getLogger(RecordServiceImpl.class);

    private final RecordRepository recordRepository;

    public RecordServiceImpl(RecordRepository recordRepository) {
        this.recordRepository = recordRepository;
    }

    @Override
    public Record save(Record record) {
        log.debug("Request to save Record : {}", record);
        return recordRepository.save(record);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Record> findAll() {
        log.debug("Request to get all Records");
        return recordRepository.findAll();
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<Record> findOne(Long id) {
        log.debug("Request to get Record : {}", id);
        return recordRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Record : {}", id);
        recordRepository.deleteById(id);
    }
}
