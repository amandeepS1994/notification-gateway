package com.abidevel.notification.notificationgateway.utility;


import java.util.Collection;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

public class ObjectMapperUtility {
    private static final ModelMapper modelMapper;

    private ObjectMapperUtility () {}
    static {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    public static <T, T1> T1  mapEntity (T entity, Class<T1> targetEntity) {
        return modelMapper.map(entity, targetEntity);
    } 

    public static <T,O> Collection<O> mapAllEntities (Collection<T> entities, Class<O> mappClass) {
        return entities.stream()
        .map((T entity) -> mapEntity(entity, mappClass))
        .collect(Collectors.toList());
        
        
   }
}

