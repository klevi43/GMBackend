package org.kylecodes.gm.repositories;

import org.kylecodes.gm.entities.Exercise;
import org.kylecodes.gm.entities.Workout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
    Optional<Exercise> findByIdAndWorkout(Long id, Workout workout);
    List<Exercise> findAllByWorkout(Workout workout);
}
