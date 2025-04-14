package org.kylecodes.gm.repositories;

import com.blazebit.persistence.CriteriaBuilderFactory;
import com.blazebit.persistence.view.EntityViewManager;
import com.blazebit.persistence.view.EntityViewSetting;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import org.kylecodes.gm.entities.Workout;
import org.kylecodes.gm.entityViews.WorkoutView;

import java.util.Optional;

public class FullWorkoutRepositoryImpl implements FullWorkoutRepository {
    private final EntityManager entityManager;

    private final CriteriaBuilderFactory criteriaBuilderFactory;

    private final EntityViewManager entityViewManager;

    public FullWorkoutRepositoryImpl(EntityManager entityManager, CriteriaBuilderFactory criteriaBuilderFactory, EntityViewManager entityViewManager) {
        this.entityManager = entityManager;
        this.criteriaBuilderFactory = criteriaBuilderFactory;
        this.entityViewManager = entityViewManager;
    }

    @Override
    public Optional<WorkoutView> findByIdAndUserIdBlaze(Long id, Long userId) {
        try {
            return Optional.of(entityViewManager.applySetting(EntityViewSetting.create(WorkoutView.class),
                            criteriaBuilderFactory.create(entityManager, Workout.class))
                    .where("id").eq(id).where("user.id").eq(userId)
                    .getSingleResult());
        } catch (Exception e) {
            return Optional.empty();
        }


    }
}
