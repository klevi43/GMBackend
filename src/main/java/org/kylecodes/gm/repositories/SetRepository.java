package org.kylecodes.gm.repositories;

import org.kylecodes.gm.entities.Set;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SetRepository extends JpaRepository<Set, Long> {

    Optional<Set> findById(Long id);
    List<Set> findAllByExercise_Id(Long exerciseId);
    void deleteById(Long id);
}
