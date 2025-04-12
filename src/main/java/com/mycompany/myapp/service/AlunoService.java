package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.AlunoDTO;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.Aluno}.
 */
public interface AlunoService {
    /**
     * Save a aluno.
     *
     * @param alunoDTO the entity to save.
     * @return the persisted entity.
     */
    Mono<AlunoDTO> save(AlunoDTO alunoDTO);

    /**
     * Updates a aluno.
     *
     * @param alunoDTO the entity to update.
     * @return the persisted entity.
     */
    Mono<AlunoDTO> update(AlunoDTO alunoDTO);

    /**
     * Partially updates a aluno.
     *
     * @param alunoDTO the entity to update partially.
     * @return the persisted entity.
     */
    Mono<AlunoDTO> partialUpdate(AlunoDTO alunoDTO);

    /**
     * Get all the alunos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Flux<AlunoDTO> findAll(Pageable pageable);

    /**
     * Returns the number of alunos available.
     * @return the number of entities in the database.
     *
     */
    Mono<Long> countAll();

    /**
     * Get the "id" aluno.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Mono<AlunoDTO> findOne(Long id);

    /**
     * Delete the "id" aluno.
     *
     * @param id the id of the entity.
     * @return a Mono to signal the deletion
     */
    Mono<Void> delete(Long id);
}
