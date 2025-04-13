package org.kylecodes.gm.repositories;

import com.blazebit.persistence.CriteriaBuilderFactory;
import com.blazebit.persistence.view.EntityViewManager;
import com.blazebit.persistence.view.EntityViewSetting;
import jakarta.persistence.EntityManager;
import org.kylecodes.gm.entities.Workout;
import org.kylecodes.gm.entityViews.WorkoutView;

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
    public WorkoutView findByIdBlaze(Long id) {
        return entityViewManager.applySetting(EntityViewSetting.create(WorkoutView.class),
                criteriaBuilderFactory.create(entityManager, Workout.class, "w")).where("w.id").eq(id)
                        .getSingleResult();


    }
}
