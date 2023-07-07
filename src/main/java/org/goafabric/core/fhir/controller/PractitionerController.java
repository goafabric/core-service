package org.goafabric.core.fhir.controller;

import org.goafabric.core.fhir.controller.vo.Bundle;
import org.goafabric.core.fhir.controller.vo.Practitioner;
import org.goafabric.core.fhir.logic.FhirLogic;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController(value = "FhirPractitionerController")
@RequestMapping(value = "fhir/Practitioner", produces = {MediaType.APPLICATION_JSON_VALUE, "application/fhir+json"})
public class PractitionerController {
	private final FhirLogic<Practitioner> practitionerLogic;

	public PractitionerController(FhirLogic<Practitioner> practitionerLogic) {
		this.practitionerLogic = practitionerLogic;
	}

	@PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, "application/fhir+json"})
	public void create(Practitioner practitioner) {
		practitionerLogic.create(practitioner);
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable String id) {
		practitionerLogic.delete(id);
	}

	@GetMapping("/{id}")
	public Practitioner getById(@PathVariable String id) {
		return practitionerLogic.getById(id);
	}

	@GetMapping
	public Bundle<Practitioner> search(@RequestParam(value = "family", required = false) String familyName) {
		var bundle = new Bundle<Practitioner>();
		//practitionerLogic.search(familyName).forEach(p -> bundle.addEntry(MockUtil.createBundleEntry(p, p.getId())));
		return bundle;
	}

}
