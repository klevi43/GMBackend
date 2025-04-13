package org.kylecodes.gm.entityViews;

import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.FetchStrategy;
import com.blazebit.persistence.view.IdMapping;
import com.blazebit.persistence.view.Mapping;
import org.kylecodes.gm.entities.Workout;

import java.util.List;

@EntityView(Workout.class)
public interface WorkoutView {
    // create methods to get the properties you want
    @IdMapping
    Long getId();
    String getName();

    @Mapping(fetch = FetchStrategy.MULTISET)
    List<ExerciseView> getExercises();
}
