package com.soprasteria.fleet.dto.dtoUtils;

import org.modelmapper.ModelMapper;

public final class DtoUtils {

    public DTOEntity convertToDto(Object obj, DTOEntity mapper) {
        return new ModelMapper().map(obj, mapper.getClass());
    }

    public Object convertToEntity(Object obj, DTOEntity mapper) {
        return new ModelMapper().map(mapper, obj.getClass());
    }

}
