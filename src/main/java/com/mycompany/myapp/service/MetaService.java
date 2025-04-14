package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.MetaDTO;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.Meta}.
 */
public interface MetaService {
    /**
     * Save a meta.
     *
     * @param metaDTO the entity to save.
     * @return the persisted entity.
     */
    Mono<MetaDTO> save(MetaDTO metaDTO);

    /**
     * Updates a meta.
     *
     * @param metaDTO the entity to update.
     * @return the persisted entity.
     */
    Mono<MetaDTO> update(MetaDTO metaDTO);

    /**
     * Partially updates a meta.
     *
     * @param metaDTO the entity to update partially.
     * @return the persisted entity.
     */
    Mono<MetaDTO> partialUpdate(MetaDTO metaDTO);

    /**
     * Get all the metas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Flux<MetaDTO> findAll(Pageable pageable);

    /**
     * Get all the metas with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Flux<MetaDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Returns the number of metas available.
     * @return the number of entities in the database.
     *
     */
    Mono<Long> countAll();

    /**
     * Get the "id" meta.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Mono<MetaDTO> findOne(Long id);

    /**
     * Delete the "id" meta.
     *
     * @param id the id of the entity.
     * @return a Mono to signal the deletion
     */
    Mono<Void> delete(Long id);
}
