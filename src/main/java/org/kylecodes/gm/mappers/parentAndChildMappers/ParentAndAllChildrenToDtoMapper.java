package org.kylecodes.gm.mappers.parentAndChildMappers;

public interface ParentAndAllChildrenToDtoMapper<T, S> {
    public S mapAllToDto(T parent);
}
