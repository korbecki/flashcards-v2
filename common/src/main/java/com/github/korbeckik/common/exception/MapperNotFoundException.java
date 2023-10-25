package com.github.korbeckik.common.exception;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;

@Getter
@Log4j2
public class MapperNotFoundException extends RuntimeException {
    private final Class<?> source;
    private final Class<?> destination;


    public MapperNotFoundException(Class<?> source, Class<?> destination) {
        this.source = source;
        this.destination = destination;
        log.error("Mapper between types: " + source.getName() + ", " + destination.getName() + " not found");
    }

}
