package org.kylecodes.gm.repositories;

import org.kylecodes.gm.entities.Workout;
import org.kylecodes.gm.entityViews.WorkoutView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WorkoutRepository extends JpaRepository<Workout, Long>, FullWorkoutRepository {
    @Query(value = "SELECT id, date, name, user_id \n"
            + "FROM (SELECT *, ROW_NUMBER() OVER \n"
            + "(PARTITION BY name ORDER BY date DESC) rn \n"
            + "FROM gmdb.workout) temp WHERE rn = 1 and user_id = :#{#userId}", nativeQuery = true)
    List<Workout> findAllMostRecentWorkoutsByUserId(Long userId);
    List<Workout> findAllByUserId(Long userId);
    Optional<Workout> findByIdAndUserId(Long id, Long userId);
    void deleteByIdAndUserId(Long id, Long userId);

    Optional<Workout> findByName(String name);
    Optional<WorkoutView> findByIdAndUserIdBlaze(Long id, Long userId);
    boolean existsByIdAndUserId(Long id, Long userId);
}
