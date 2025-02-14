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
            + "FROM gmdb.workout) temp WHERE rn = 1", nativeQuery = true)
    List<Workout> findAllMostRecent();
    Optional<Workout> findById(Long id);
    void deleteById(Long id);
    Optional<Workout> findByName(String name);
}
