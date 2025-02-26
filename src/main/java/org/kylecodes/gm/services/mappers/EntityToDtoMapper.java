package org.kylecodes.gm.services.mappers;

public interface EntityToDtoMapper<T, S> {
    S mapToDto(T entity);
}
