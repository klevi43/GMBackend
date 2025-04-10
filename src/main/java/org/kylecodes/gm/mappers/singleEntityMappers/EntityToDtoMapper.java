package org.kylecodes.gm.mappers.singleEntityMappers;

public interface EntityToDtoMapper<T, S> {
    S mapToDto(T entity);
}
