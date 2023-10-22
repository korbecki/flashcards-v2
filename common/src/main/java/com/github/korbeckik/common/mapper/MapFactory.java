package com.github.korbeckik.common.mapper;

import com.github.korbeckik.common.exception.MapperNotFoundException;
import lombok.experimental.UtilityClass;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@UtilityClass
public class MapFactory {

    private static final List<Mapper<?, ?>> mapperList = new ArrayList<>();

    public static void registerMappers(Mapper<?, ?>... mappers) {
        mapperList.addAll(List.of(mappers));
    }

    @SuppressWarnings("unchecked")
    public static <D> D map(Object source, Class<D> destination) {
        return  mapperList
                .stream()
                .filter(mapperFilter(source, destination))
                .findFirst()
                .map(mapper -> {
                    if (mapper.getSource().equals(source.getClass())) {
                        return mapper.sourceToDestination(source);
                    } else {
                        return mapper.destinationToSource(source);
                    }
                })
                .map(mapper -> (D)mapper)
                .orElseThrow(() -> new MapperNotFoundException(source.getClass(), destination));
    }


    private static <S, D> Predicate<Mapper<?, ?>> mapperFilter(S source, Class<D> destination) {
        return mapper -> mapper.getSource().equals(source.getClass()) && mapper.getDestination().equals(destination)
                || mapper.getSource().equals(destination) && mapper.getDestination().equals(source.getClass());
    }


}
