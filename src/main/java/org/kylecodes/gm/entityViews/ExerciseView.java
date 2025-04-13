package org.kylecodes.gm.entityViews;

import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.IdMapping;
import org.kylecodes.gm.entities.Exercise;

@EntityView(Exercise.class)
public interface ExerciseView {

    @IdMapping
    Long getId();
    String getName();
//    @Mapping
//    List<SetView> getSets();
}
