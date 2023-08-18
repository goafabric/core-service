package org.goafabric.core.ui.adapter;

import org.goafabric.core.mrc.controller.vo.BodyMetrics;
import org.goafabric.core.mrc.logic.BodyMetricsLogic;
import org.springframework.stereotype.Component;

@Component
public class BodyMetricsAdapter {
    private final BodyMetricsLogic bodyMetricsLogic;

    public BodyMetricsAdapter(BodyMetricsLogic bodyMetricsLogic) {
        this.bodyMetricsLogic = bodyMetricsLogic;
    }

    public BodyMetrics getById(String id) {
        return bodyMetricsLogic.getById(id);
    }
}
