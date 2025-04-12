package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.repository.AlunoRepository;
import com.mycompany.myapp.service.AlunoService;
import com.mycompany.myapp.service.dto.AlunoDTO;
import com.mycompany.myapp.service.mapper.AlunoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Service Implementation for managing {@link com.mycompany.myapp.domain.Aluno}.
 */
@Service
@Transactional
public class AlunoServiceImpl implements AlunoService {

    private static final Logger LOG = LoggerFactory.getLogger(AlunoServiceImpl.class);

    private final AlunoRepository alunoRepository;

    private final AlunoMapper alunoMapper;

    public AlunoServiceImpl(AlunoRepository alunoRepository, AlunoMapper alunoMapper) {
        this.alunoRepository = alunoRepository;
        this.alunoMapper = alunoMapper;
    }

    @Override
    public Mono<AlunoDTO> save(AlunoDTO alunoDTO) {
        LOG.debug("Request to save Aluno : {}", alunoDTO);
        return alunoRepository.save(alunoMapper.toEntity(alunoDTO)).map(alunoMapper::toDto);
    }

    @Override
    public Mono<AlunoDTO> update(AlunoDTO alunoDTO) {
        LOG.debug("Request to update Aluno : {}", alunoDTO);
        return alunoRepository.save(alunoMapper.toEntity(alunoDTO)).map(alunoMapper::toDto);
    }

    @Override
    public Mono<AlunoDTO> partialUpdate(AlunoDTO alunoDTO) {
        LOG.debug("Request to partially update Aluno : {}", alunoDTO);

        return alunoRepository
            .findById(alunoDTO.getId())
            .map(existingAluno -> {
                alunoMapper.partialUpdate(existingAluno, alunoDTO);

                return existingAluno;
            })
            .flatMap(alunoRepository::save)
            .map(alunoMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Flux<AlunoDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all Alunos");
        return alunoRepository.findAllBy(pageable).map(alunoMapper::toDto);
    }

    public Mono<Long> countAll() {
        return alunoRepository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public Mono<AlunoDTO> findOne(Long id) {
        LOG.debug("Request to get Aluno : {}", id);
        return alunoRepository.findById(id).map(alunoMapper::toDto);
    }

    @Override
    public Mono<Void> delete(Long id) {
        LOG.debug("Request to delete Aluno : {}", id);
        return alunoRepository.deleteById(id);
    }
}
