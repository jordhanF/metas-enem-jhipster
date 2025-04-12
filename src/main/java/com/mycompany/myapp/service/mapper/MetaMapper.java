package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Meta;
import com.mycompany.myapp.service.dto.MetaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Meta} and its DTO {@link MetaDTO}.
 */
@Mapper(componentModel = "spring")
public interface MetaMapper extends EntityMapper<MetaDTO, Meta> {}
