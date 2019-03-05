package org.duohuo.paper.model.convert;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Converter<R, E> {

    private Function<R, E> fromResult;

    private Function<E, R> fromEntity;

    public Converter(final Function<R, E> fromResult, final Function<E, R> fromEntity) {
        this.fromResult = fromResult;
        this.fromEntity = fromEntity;
    }

    public final E convertFromResult(final R result) {
        return fromResult.apply(result);
    }

    public final R convertFromEntity(final E entity) {
        return fromEntity.apply(entity);
    }

    public final List<E> batchConvertFromResult(final List<R> resultList) {
        return resultList.stream().map(this::convertFromResult).collect(Collectors.toList());
    }

    public final List<R> batchConvertFromEntity(final List<E> entityList) {
        return entityList.stream().map(this::convertFromEntity).collect(Collectors.toList());
    }
}
