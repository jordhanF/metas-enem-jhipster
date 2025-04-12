package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.repository.MetaRepository;
import com.mycompany.myapp.service.MetaService;
import com.mycompany.myapp.service.dto.MetaDTO;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.reactive.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.Meta}.
 */
@RestController
@RequestMapping("/api/metas")
public class MetaResource {

    private static final Logger LOG = LoggerFactory.getLogger(MetaResource.class);

    private static final String ENTITY_NAME = "meta";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MetaService metaService;

    private final MetaRepository metaRepository;

    public MetaResource(MetaService metaService, MetaRepository metaRepository) {
        this.metaService = metaService;
        this.metaRepository = metaRepository;
    }

    /**
     * {@code POST  /metas} : Create a new meta.
     *
     * @param metaDTO the metaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new metaDTO, or with status {@code 400 (Bad Request)} if the meta has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public Mono<ResponseEntity<MetaDTO>> createMeta(@Valid @RequestBody MetaDTO metaDTO) throws URISyntaxException {
        LOG.debug("REST request to save Meta : {}", metaDTO);
        if (metaDTO.getId() != null) {
            throw new BadRequestAlertException("A new meta cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return metaService
            .save(metaDTO)
            .map(result -> {
                try {
                    return ResponseEntity.created(new URI("/api/metas/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    /**
     * {@code PUT  /metas/:id} : Updates an existing meta.
     *
     * @param id the id of the metaDTO to save.
     * @param metaDTO the metaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated metaDTO,
     * or with status {@code 400 (Bad Request)} if the metaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the metaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public Mono<ResponseEntity<MetaDTO>> updateMeta(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody MetaDTO metaDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Meta : {}, {}", id, metaDTO);
        if (metaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, metaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return metaRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                return metaService
                    .update(metaDTO)
                    .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                    .map(result ->
                        ResponseEntity.ok()
                            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                            .body(result)
                    );
            });
    }

    /**
     * {@code PATCH  /metas/:id} : Partial updates given fields of an existing meta, field will ignore if it is null
     *
     * @param id the id of the metaDTO to save.
     * @param metaDTO the metaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated metaDTO,
     * or with status {@code 400 (Bad Request)} if the metaDTO is not valid,
     * or with status {@code 404 (Not Found)} if the metaDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the metaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public Mono<ResponseEntity<MetaDTO>> partialUpdateMeta(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody MetaDTO metaDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Meta partially : {}, {}", id, metaDTO);
        if (metaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, metaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        return metaRepository
            .existsById(id)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound"));
                }

                Mono<MetaDTO> result = metaService.partialUpdate(metaDTO);

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
     * {@code GET  /metas} : get all the metas.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of metas in body.
     */
    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<List<MetaDTO>> getAllMetas(@RequestParam(name = "filter", required = false) String filter) {
        if ("aluno-is-null".equals(filter)) {
            LOG.debug("REST request to get all Metas where aluno is null");
            return metaService.findAllWhereAlunoIsNull().collectList();
        }
        LOG.debug("REST request to get all Metas");
        return metaService.findAll().collectList();
    }

    /**
     * {@code GET  /metas} : get all the metas as a stream.
     * @return the {@link Flux} of metas.
     */
    @GetMapping(value = "", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<MetaDTO> getAllMetasAsStream() {
        LOG.debug("REST request to get all Metas as a stream");
        return metaService.findAll();
    }

    /**
     * {@code GET  /metas/:id} : get the "id" meta.
     *
     * @param id the id of the metaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the metaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public Mono<ResponseEntity<MetaDTO>> getMeta(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Meta : {}", id);
        Mono<MetaDTO> metaDTO = metaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(metaDTO);
    }

    /**
     * {@code DELETE  /metas/:id} : delete the "id" meta.
     *
     * @param id the id of the metaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteMeta(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Meta : {}", id);
        return metaService
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
