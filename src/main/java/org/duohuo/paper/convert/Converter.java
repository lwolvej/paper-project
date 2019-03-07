package org.duohuo.paper.convert;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

abstract class Converter<R, E> {

    private Function<E, R> fromOrigin;

    Converter(final Function<E, R> fromOrigin) {
        this.fromOrigin = fromOrigin;
    }

    final R convertFromOrigin(final E entity) {
        return fromOrigin.apply(entity);
    }

    final List<R> batchConvertFromOrigin(final List<E> entityList) {
        return entityList.stream().map(this::convertFromOrigin).collect(Collectors.toList());
    }
}
