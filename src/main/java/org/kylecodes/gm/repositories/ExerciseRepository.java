package org.kylecodes.gm.repositories;

import org.kylecodes.gm.entities.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
    Optional<Exercise> findByIdAndWorkoutId(Long id, Long workoutId);
    List<Exercise> findAllByWorkoutId(Long workoutId);
    boolean existsByIdAndWorkoutId(Long id, Long workoutId);
    @Query(value = "FROM Exercise e JOIN FETCH e.sets as s " +
            " WHERE e.workout.id = :workoutId")
    List<Exercise> findAllExercisesAndSetsByWorkoutId(Long workoutId);
}
