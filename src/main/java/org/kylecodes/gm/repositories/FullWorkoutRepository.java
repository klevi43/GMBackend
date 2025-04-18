package org.kylecodes.gm.repositories;

import org.kylecodes.gm.entityViews.WorkoutView;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FullWorkoutRepository {
    Optional<WorkoutView> findByIdAndUserIdBlaze(Long id, Long userId);
}
