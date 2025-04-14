package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.repository.MetaRepository;
import com.mycompany.myapp.service.MetaService;
import com.mycompany.myapp.service.dto.MetaDTO;
import com.mycompany.myapp.service.mapper.MetaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Implementation for managing {@link com.mycompany.myapp.domain.Meta}.
 */
@Service
@Transactional
public class MetaServiceImpl implements MetaService {

    private static final Logger LOG = LoggerFactory.getLogger(MetaServiceImpl.class);

    private final MetaRepository metaRepository;

    private final MetaMapper metaMapper;

    public MetaServiceImpl(MetaRepository metaRepository, MetaMapper metaMapper) {
        this.metaRepository = metaRepository;
        this.metaMapper = metaMapper;
    }

    @Override
    public Mono<MetaDTO> save(MetaDTO metaDTO) {
        LOG.debug("Request to save Meta : {}", metaDTO);
        return metaRepository.save(metaMapper.toEntity(metaDTO)).map(metaMapper::toDto);
    }

    @Override
    public Mono<MetaDTO> update(MetaDTO metaDTO) {
        LOG.debug("Request to update Meta : {}", metaDTO);
        return metaRepository.save(metaMapper.toEntity(metaDTO)).map(metaMapper::toDto);
    }

    @Override
    public Mono<MetaDTO> partialUpdate(MetaDTO metaDTO) {
        LOG.debug("Request to partially update Meta : {}", metaDTO);

        return metaRepository
            .findById(metaDTO.getId())
            .map(existingMeta -> {
                metaMapper.partialUpdate(existingMeta, metaDTO);

                return existingMeta;
            })
            .flatMap(metaRepository::save)
            .map(metaMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Flux<MetaDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all Metas");
        return metaRepository.findAllBy(pageable).map(metaMapper::toDto);
    }

    public Flux<MetaDTO> findAllWithEagerRelationships(Pageable pageable) {
        return metaRepository.findAllWithEagerRelationships(pageable).map(metaMapper::toDto);
    }

    public Mono<Long> countAll() {
        return metaRepository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public Mono<MetaDTO> findOne(Long id) {
        LOG.debug("Request to get Meta : {}", id);
        return metaRepository.findOneWithEagerRelationships(id).map(metaMapper::toDto);
    }

    @Override
    public Mono<Void> delete(Long id) {
        LOG.debug("Request to delete Meta : {}", id);
        return metaRepository.deleteById(id);
    }
}
