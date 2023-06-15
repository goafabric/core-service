package org.goafabric.core.data.logic;

import org.goafabric.core.data.controller.dto.Address;
import org.goafabric.core.data.controller.dto.Patient;
import org.goafabric.core.data.persistence.domain.AddressBo;
import org.goafabric.core.data.persistence.domain.PatientBo;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.Collections;
import java.util.List;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PatientMapper {
    Patient map(PatientBo value);

    PatientBo map(Patient value);

    List<Patient> map(List<PatientBo> value);

    default List<Address> mapAdresses(AddressBo value) { return Collections.singletonList(map(value)); }
    Address map(AddressBo value);

    default AddressBo mapAddresses(List<Address> value) { return map(value.get(0)); }
    AddressBo map(Address value);

}
