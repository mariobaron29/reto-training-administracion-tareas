package com.sofka.tareas.common;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;

public interface ObjectMapperDomain {
    default <T, E> List<E> mapear(List<T> list, Function<T, E> transform) {
        return list.stream().map(transform).collect(Collectors.toList());
    }

    default <K, T, E> List<E> extractAndMap(Optional<K> opt, Function<K, List<T>> extractor, Function<T, E> tranformer) {
        return opt.map(extractor).map(l -> mapear(l, tranformer)).orElse(emptyList());
    }
}
