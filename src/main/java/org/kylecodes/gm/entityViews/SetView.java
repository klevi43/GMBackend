package org.kylecodes.gm.entityViews;

import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.IdMapping;
import com.blazebit.persistence.view.Mapping;
import org.kylecodes.gm.entities.Set;

@EntityView(Set.class)
public interface SetView {
    @IdMapping
    Long getId();

    Integer getWeight();
    Integer getReps();
    @Mapping("exercise.id")
    Long getExerciseId();
}
