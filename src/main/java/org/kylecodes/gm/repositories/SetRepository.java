package org.kylecodes.gm.repositories;

import org.kylecodes.gm.entities.Set;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SetRepository extends JpaRepository<Set, Long> {

    Optional<Set> findByIdAndExerciseId(Long id, Long exerciseId);
    List<Set> findAllByExerciseId(Long exerciseId);
    boolean existsByIdAndExerciseId(Long id, Long exerciseId);
    void deleteByIdAndExerciseId(Long id, Long exerciseId);
}
