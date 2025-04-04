package org.kylecodes.gm.repositories;

import org.kylecodes.gm.entities.User;
import org.kylecodes.gm.entities.Workout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WorkoutRepository extends JpaRepository<Workout, Long> {
    @Query(value = "SELECT id, date, name, user_id \n"
            + "FROM (SELECT *, ROW_NUMBER() OVER \n"
            + "(PARTITION BY name ORDER BY date DESC) rn \n"
            + "FROM gmdb.workout) temp WHERE rn = 1 and user_id=user_id", nativeQuery = true)
    List<Workout> findAllMostRecentWorkoutsByUser(User user);
    List<Workout> findAllByUser(User user);
    Optional<Workout> findByIdAndUser(Long id, User user);
    void deleteById(Long id);
    Optional<Workout> findByName(String name);
}
