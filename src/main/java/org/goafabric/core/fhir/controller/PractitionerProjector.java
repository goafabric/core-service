package org.goafabric.core.fhir.controller;

import org.goafabric.core.data.logic.PractitionerLogic;
import org.goafabric.core.fhir.logic.mapper.FhirPractitionerMapper;
import org.goafabric.core.fhir.controller.vo.Bundle;
import org.goafabric.core.fhir.controller.vo.Practitioner;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "fhir/Practitioner", produces = {MediaType.APPLICATION_JSON_VALUE, "application/fhir+json"})
public class PractitionerProjector {
	private final PractitionerLogic logic;
	private final FhirPractitionerMapper mapper;

	public PractitionerProjector(PractitionerLogic logic, FhirPractitionerMapper mapper) {
		this.logic = logic;
		this.mapper = mapper;
	}

	@PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, "application/fhir+json"})
	public void create(Practitioner practitioner) {
		logic.save(mapper.map(practitioner));
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
				.stream().map(o -> new Bundle.BundleEntryComponent<>(o, o.getClass().getSimpleName() + "/" + o.id)).toList());
	}

}
