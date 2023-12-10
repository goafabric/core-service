package org.goafabric.core.ui.adapter;

import org.goafabric.core.organization.controller.PractitionerController;
import org.goafabric.core.organization.controller.dto.Practitioner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PractitionerAdapter implements SearchAdapter<Practitioner> {
    private final PractitionerController practitionerLogic;

    public PractitionerAdapter(PractitionerController practitionerLogic) {
        this.practitionerLogic = practitionerLogic;
    }

    @Override
    public List<Practitioner> search(String search) {
        return practitionerLogic.findByFamilyName(search);
    }

    public void save(Practitioner practitioner) {
        practitionerLogic.save(practitioner);
    }

    public void delete(String id) {
        practitionerLogic.deleteById(id);
    }
}
