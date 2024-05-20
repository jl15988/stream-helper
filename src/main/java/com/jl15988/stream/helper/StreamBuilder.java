package com.jl15988.stream.helper;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Jalon
 * @since 2024/5/20 13:50
 **/
public class StreamBuilder<T> {

    private Collection<T> list;

    StreamBuilder(Collection<T> list) {
        this.list = list;
    }

    public static <T> StreamBuilder<T> build(Collection<T> list) {
        return new StreamBuilder<T>(list);
    }

    public <S, F> Map<S, F> toMap(Function<? super T, S> mapper, Function<? super T, F> mapper2) {
        return this.list.stream().collect(Collectors.toMap(mapper, mapper2, (v1, v2) -> v1));
    }
}
