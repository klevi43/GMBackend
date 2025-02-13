package org.kylecodes.gm.repositories;

import org.kylecodes.gm.entities.Workout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WorkoutRepository extends JpaRepository<Workout, Long> {
    Optional<Workout> findById(Long id);
    void deleteById(Long id);
    Optional<Workout> findByName(String name);
}
