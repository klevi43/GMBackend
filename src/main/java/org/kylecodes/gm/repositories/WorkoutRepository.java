package org.kylecodes.gm.repositories;

import jakarta.transaction.Transactional;
import org.kylecodes.gm.entities.Workout;
import org.kylecodes.gm.entityViews.WorkoutView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WorkoutRepository extends JpaRepository<Workout, Long>, FullWorkoutRepository {
    @Query(value = "SELECT id, date, name, user_id \n"
            + "FROM (SELECT *, ROW_NUMBER() OVER \n"
            + "(PARTITION BY name ORDER BY date DESC) rn \n"
            + "FROM workout WHERE user_id = :userId) temp WHERE rn = 1", nativeQuery = true)
    List<Workout> findAllMostRecentWorkoutsByUserId(Long userId);
    Page<Workout> findAllByUserIdOrderByDateDesc(Long userId, Pageable pageable);
    Optional<Workout> findByIdAndUserId(Long id, Long userId);
    @Modifying
    @Transactional
    @Query(value = "UPDATE workout w SET w.name = :newName WHERE w.name = :currentName", nativeQuery = true)
    int updateAllByName(String newName, String currentName);
    void deleteByIdAndUserId(Long id, Long userId);
    Optional<WorkoutView> findByIdAndUserIdBlaze(Long id, Long userId);
    boolean existsByIdAndUserId(Long id, Long userId);
}
