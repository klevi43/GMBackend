package org.kylecodes.gm.repositories;

import org.kylecodes.gm.entities.Workout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WorkoutRepository extends JpaRepository<Workout, Long> {
    @Query(value = "SELECT id, date, name \n"
            + "FROM (SELECT *, ROW_NUMBER() OVER \n"
            + "(PARTITION BY name ORDER BY date DESC) rn \n"
            + "FROM gmdb.workout) temp WHERE rn = 1 and user_id= :#{#userId}", nativeQuery = true)
    List<Workout> findAllMostRecentWorkoutsByUserId(Long userId);
    List<Workout> findAllByUserId(Long userId);
    Optional<Workout> findByIdAndUserId(Long id, Long userId);
    void deleteByIdAndUserId(Long id, Long userId);

    Optional<Workout> findByName(String name);
//    @Query(value = "FROM Workout w JOIN FETCH w.exercises as e " +
//            " WHERE w.id = :id AND w.user.id = :userId")

    @Query(value = "SELECT * FROM workout w " +
            " JOIN exercise e ON e.workout_id = w.id" +
            " JOIN ex_set s ON s.exercise_id = e.id WHERE w.id = 2252", nativeQuery = true)
    Optional<Workout> findParentAndChildrenByIdAndUserId(Long id, Long userId);
    boolean existsByIdAndUserId(Long id, Long userId);
}
