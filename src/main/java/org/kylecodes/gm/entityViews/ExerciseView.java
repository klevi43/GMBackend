package org.kylecodes.gm.entityViews;

import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.FetchStrategy;
import com.blazebit.persistence.view.IdMapping;
import com.blazebit.persistence.view.Mapping;
import org.kylecodes.gm.entities.Exercise;

import java.util.List;

@EntityView(Exercise.class)
public interface ExerciseView {

    @IdMapping
    Long getId();
    String getName();
    @Mapping("workout.id")
    Long getWorkoutId();
    @Mapping(fetch = FetchStrategy.MULTISET)
    List<SetView> getSets();
}
