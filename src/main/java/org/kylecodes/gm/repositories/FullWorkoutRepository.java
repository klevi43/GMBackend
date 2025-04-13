package org.kylecodes.gm.repositories;

import org.kylecodes.gm.entityViews.WorkoutView;
import org.springframework.stereotype.Repository;

@Repository
public interface FullWorkoutRepository {
    WorkoutView findByIdBlaze(Long id);
}
