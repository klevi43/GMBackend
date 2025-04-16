package org.kylecodes.gm.entityViews;

import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.IdMapping;
import org.kylecodes.gm.entities.Set;

@EntityView(Set.class)
public interface SetView {
    @IdMapping
    Long getId();

    Integer getWeight();
    Integer getReps();
}
