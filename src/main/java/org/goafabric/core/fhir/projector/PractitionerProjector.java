package org.goafabric.core.fhir.projector;

import org.goafabric.core.data.logic.PractitionerLogic;
import org.goafabric.core.fhir.projector.mapper.FPractitionerMapper;
import org.goafabric.core.fhir.projector.vo.Bundle;
import org.goafabric.core.fhir.projector.vo.Practitioner;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "fhir/Practitioner", produces = {MediaType.APPLICATION_JSON_VALUE, "application/fhir+json"})
public class PractitionerProjector {
	private final PractitionerLogic logic;
	private final FPractitionerMapper mapper;

	public PractitionerProjector(PractitionerLogic logic, FPractitionerMapper mapper) {
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
		var bundle = new Bundle<Practitioner>();
		mapper.map(logic.findByGivenName(familyName))
				.forEach(o -> bundle.addEntry(new Bundle.BundleEntryComponent(o, o.getClass().getSimpleName() + "/" + o.id)));
		return bundle;
	}

}
