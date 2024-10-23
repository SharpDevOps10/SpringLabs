package com.daniorerio.lost_found.mapper;

import com.daniorerio.lost_found.DTO.UpdateLocationDto;
import com.daniorerio.lost_found.entities.Location;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface LocationMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateLocationFromDto(UpdateLocationDto dto, @MappingTarget Location entity);
}