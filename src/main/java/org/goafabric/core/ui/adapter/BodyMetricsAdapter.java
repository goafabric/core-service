package org.goafabric.core.ui.adapter;

import org.goafabric.core.medicalrecords.controller.vo.BodyMetrics;
import org.goafabric.core.medicalrecords.logic.BodyMetricsLogic;
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
