package org.kylecodes.gm.repositories;

import org.kylecodes.gm.dtos.ExerciseDataDto;
import org.kylecodes.gm.entities.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ExerciseStatRepository extends JpaRepository<Set, Long> {

    @Query("""
    SELECT new org.kylecodes.gm.dtos.ExerciseDataDto(s.exercise.id, s.exercise.name, s.weight, s.reps, s.exercise.workout.date)
    FROM set_tbl s WHERE s.exercise.workout.user.id = :userId
        AND s.exercise.name = :exerciseName
        AND s.weight = (
            SELECT MAX(s2.weight)
            FROM set_tbl s2
            WHERE s2.exercise.id = s.exercise.id
                AND s2.exercise.workout.user.id = :userId
    )
""")
    List<ExerciseDataDto> findMaxWeightforExerciseForUser(@Param("userId")Long userId, @Param("exerciseName") String exerciseName);
}
