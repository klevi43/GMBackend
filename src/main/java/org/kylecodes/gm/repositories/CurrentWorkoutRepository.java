package org.kylecodes.gm.repositories;

import org.kylecodes.gm.entities.Workout;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CurrentWorkoutRepository extends JpaRepository<Workout, Long> {
    Optional<Workout> findByName(String name);

    void deleteByName(String name);

}
