package com.github.korbeckik.common.mapper;

import java.util.Collection;

public interface Mapper<S, D> {
    Class<S> getSource();

    Class<D> getDestination();

    D sourceToDestination(Object source);

    S destinationToSource(Object dest);

    default Collection<D> sourceToDestination(Collection<S> destList) {
        return destList.stream()
                .map(this::sourceToDestination)
                .toList();
    }

    default Collection<S> destinationToSource(Collection<D> destList) {
        return destList.stream()
                .map(this::destinationToSource)
                .toList();
    }
}
