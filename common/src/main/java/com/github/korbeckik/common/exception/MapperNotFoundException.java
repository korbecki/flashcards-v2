package com.github.korbeckik.common.exception;


public class MapperNotFoundException extends RuntimeException{
    private Class<?> source;
    private Class<?> destination;


    public MapperNotFoundException(Class<?> source,  Class<?> destination) {
        this.source = source;
        this.destination = destination;
    }

}
