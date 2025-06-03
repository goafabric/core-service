package org.goafabric.core.fhir.r4.controller;

import org.goafabric.core.organization.logic.PractitionerLogic;
import org.goafabric.core.fhir.r4.logic.mapper.FhirPractitionerMapper;
import org.goafabric.core.fhir.r4.controller.dto.Bundle;
import org.goafabric.core.fhir.r4.controller.dto.Practitioner;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "fhir/r4/Practitioner", produces = {MediaType.APPLICATION_JSON_VALUE, "application/fhir+json"})
public class PractitionerFhirController {
	private final PractitionerLogic logic;
	private final FhirPractitionerMapper mapper;

	public PractitionerFhirController(PractitionerLogic logic, FhirPractitionerMapper mapper) {
		this.logic = logic;
		this.mapper = mapper;
	}

	@PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, "application/fhir+json"})
	public void create(Practitioner practitioner) {
		throw new IllegalStateException("NYI");
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable String id) {
		logic.deleteById(id);
	}

	@GetMapping("/{id}")
	public Practitioner getById(@PathVariable String id) {
		return mapper.map(logic.getById(id));
	}

	@GetMapping
	public Bundle<Practitioner> search(@RequestParam(value = "family", required = false) String familyName) {
		return new Bundle<>(mapper.map(logic.findByFamilyName(familyName))
				.stream().map(o -> new Bundle.BundleEntryComponent<>(o, o.getClass().getSimpleName() + "/" + o.id())).toList());
	}

}
