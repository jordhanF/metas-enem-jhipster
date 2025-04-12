package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.MetaDTO;
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
     * @return the list of entities.
     */
    Flux<MetaDTO> findAll();

    /**
     * Get all the MetaDTO where Aluno is {@code null}.
     *
     * @return the {@link Flux} of entities.
     */
    Flux<MetaDTO> findAllWhereAlunoIsNull();

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
