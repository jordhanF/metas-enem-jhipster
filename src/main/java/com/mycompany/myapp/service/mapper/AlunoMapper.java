package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Aluno;
import com.mycompany.myapp.domain.Meta;
import com.mycompany.myapp.service.dto.AlunoDTO;
import com.mycompany.myapp.service.dto.MetaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Aluno} and its DTO {@link AlunoDTO}.
 */
@Mapper(componentModel = "spring")
public interface AlunoMapper extends EntityMapper<AlunoDTO, Aluno> {
    @Mapping(target = "meta", source = "meta", qualifiedByName = "metaId")
    AlunoDTO toDto(Aluno s);

    @Named("metaId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    MetaDTO toDtoMetaId(Meta meta);
}
