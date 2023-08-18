package org.goafabric.core.ui.adapter;

import org.goafabric.core.data.controller.vo.Practitioner;
import org.goafabric.core.data.logic.PractitionerLogic;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PractitionerAdapter implements SearchAdapter<Practitioner> {
    private final PractitionerLogic practitionerLogic;

    public PractitionerAdapter(PractitionerLogic practitionerLogic) {
        this.practitionerLogic = practitionerLogic;
    }

    @Override
    public List<Practitioner> search(String search) {
        return practitionerLogic.findByFamilyName(search);
    }

}
