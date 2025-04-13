package org.kylecodes.gm.configurations;

import com.blazebit.persistence.Criteria;
import com.blazebit.persistence.CriteriaBuilderFactory;
import com.blazebit.persistence.spi.CriteriaBuilderConfiguration;
import com.blazebit.persistence.view.EntityViewManager;
import com.blazebit.persistence.view.EntityViews;
import com.blazebit.persistence.view.spi.EntityViewConfiguration;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnit;
import org.kylecodes.gm.entityViews.ExerciseView;
import org.kylecodes.gm.entityViews.WorkoutView;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;

@Configuration
public class BlazePersistenceConfig {
    @PersistenceUnit
    private EntityManagerFactory entityManagerFactory;

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    @Lazy(false)
    public CriteriaBuilderFactory createCriteriaBuilderFactory() {
        CriteriaBuilderConfiguration config = Criteria.getDefault();
        // do some configuration
        return config.createCriteriaBuilderFactory(entityManagerFactory);
    }
    @Bean
    public EntityViewConfiguration entityViewConfiguration(
            CriteriaBuilderFactory criteriaBuilderFactory
    ) {
        EntityViewConfiguration entityViewConfiguration = EntityViews.createDefaultConfiguration();
        entityViewConfiguration.addEntityView(WorkoutView.class);
        entityViewConfiguration.addEntityView(ExerciseView.class);
        return entityViewConfiguration;
    }

    @Bean
    public EntityViewManager entityViewManager(
            CriteriaBuilderFactory criteriaBuilderFactory,
            EntityViewConfiguration entityViewConfiguration
    ) {
        return entityViewConfiguration.createEntityViewManager(criteriaBuilderFactory);
    }

}
