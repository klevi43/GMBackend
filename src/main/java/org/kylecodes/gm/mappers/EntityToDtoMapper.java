package org.kylecodes.gm.mappers;

public interface EntityToDtoMapper<T, S> {
    S mapToDto(T entity);
}
