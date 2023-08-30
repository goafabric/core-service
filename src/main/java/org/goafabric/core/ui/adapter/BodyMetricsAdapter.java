package org.goafabric.core.ui.adapter;

import org.goafabric.core.medicalrecords.controller.BodyMetricsController;
import org.goafabric.core.medicalrecords.controller.vo.BodyMetrics;
import org.springframework.stereotype.Component;

@Component
public class BodyMetricsAdapter {
    private final BodyMetricsController bodyMetricsLogic;

    public BodyMetricsAdapter(BodyMetricsController bodyMetricsLogic) {
        this.bodyMetricsLogic = bodyMetricsLogic;
    }

    public BodyMetrics getById(String id) {
        return bodyMetricsLogic.getById(id);
    }
}
