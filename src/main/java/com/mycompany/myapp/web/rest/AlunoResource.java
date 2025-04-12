package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.AlunoRepository;
import com.mycompany.myapp.service.AlunoService;
import com.mycompany.myapp.service.dto.AlunoDTO;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.ForwardedHeaderUtils;
import reactor.core.publisher.Mono;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.reactive.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.Aluno}.
 */
@RestController
@RequestMapping("/api/alunos")
public class AlunoResource {

    private static final Logger LOG = LoggerFactory.getLogger(AlunoResource.class);

    private static final String ENTITY_NAME = "aluno";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AlunoService alunoService;

    private final AlunoRepository alunoRepository;

    public AlunoResource(AlunoService alunoService, AlunoRepository alunoRepository) {
        this.alunoService = alunoService;
        this.alunoRepository = alunoRepository;
    }

    /**
     * {@code POST  /alunos} : Create a new aluno.
     *
     * @param alunoDTO the alunoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new alunoDTO, or with status {@code 400 (Bad Request)} if the aluno has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public Mono<ResponseEntity<AlunoDTO>> createAluno(@Valid @RequestBody AlunoDTO alunoDTO) throws URISyntaxException {
        LOG.debug("REST request to save Aluno : {}", alunoDTO);
        if (alunoDTO.getId() != null) {
            throw new BadRequestAlertException("A new aluno cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return alunoService
            .save(alunoDTO)
            .map(result -> {
                try {
                    return ResponseEntity.created(new URI("/api/alunos/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    /**
     * {@code PUT  /alunos/:id} : Updates an existing aluno.
     *
     * @param id the id of the alunoDTO to save.
     * @param alunoDTO the alunoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated alunoDTO,
     * or with status {@code 400 (Bad Request)} if the alunoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the alunoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public Mono<ResponseEntity<AlunoDTO>> updateAluno(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AlunoDTO alunoDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Aluno : {}, {}", id, alunoDTO);
        if (alunoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, alunoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return alunoRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                return alunoService
                    .update(alunoDTO)
                    .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                    .map(result ->
                        ResponseEntity.ok()
                            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                            .body(result)
                    );
            });
    }

    /**
     * {@code PATCH  /alunos/:id} : Partial updates given fields of an existing aluno, field will ignore if it is null
     *
     * @param id the id of the alunoDTO to save.
     * @param alunoDTO the alunoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated alunoDTO,
     * or with status {@code 400 (Bad Request)} if the alunoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the alunoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the alunoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Mono<ResponseEntity<AlunoDTO>> partialUpdateAluno(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AlunoDTO alunoDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Aluno partially : {}, {}", id, alunoDTO);
        if (alunoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, alunoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return alunoRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                Mono<AlunoDTO> result = alunoService.partialUpdate(alunoDTO);

                return result
                    .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                    .map(res ->
                        ResponseEntity.ok()
                            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, res.getId().toString()))
                            .body(res)
                    );
            });
    }

    /**
     * {@code GET  /alunos} : get all the alunos.
     *
     * @param pageable the pagination information.
     * @param request a {@link ServerHttpRequest} request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of alunos in body.
     */
    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<List<AlunoDTO>>> getAllAlunos(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        ServerHttpRequest request
    ) {
        LOG.debug("REST request to get a page of Alunos");
        return alunoService
            .countAll()
            .zipWith(alunoService.findAll(pageable).collectList())
            .map(countWithEntities ->
                ResponseEntity.ok()
                    .headers(
                        PaginationUtil.generatePaginationHttpHeaders(
                            ForwardedHeaderUtils.adaptFromForwardedHeaders(request.getURI(), request.getHeaders()),
                            new PageImpl<>(countWithEntities.getT2(), pageable, countWithEntities.getT1())
                        )
                    )
                    .body(countWithEntities.getT2())
            );
    }

    /**
     * {@code GET  /alunos/:id} : get the "id" aluno.
     *
     * @param id the id of the alunoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the alunoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public Mono<ResponseEntity<AlunoDTO>> getAluno(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Aluno : {}", id);
        Mono<AlunoDTO> alunoDTO = alunoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(alunoDTO);
    }

    /**
     * {@code DELETE  /alunos/:id} : delete the "id" aluno.
     *
     * @param id the id of the alunoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteAluno(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Aluno : {}", id);
        return alunoService
            .delete(id)
            .then(
                Mono.just(
                    ResponseEntity.noContent()
                        .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
                        .build()
                )
            );
    }
}
