package org.goafabric.core.data.logic;

import org.goafabric.core.data.controller.dto.Address;
import org.goafabric.core.data.controller.dto.ContactPoint;
import org.goafabric.core.data.persistence.domain.AddressBo;
import org.goafabric.core.data.persistence.domain.ContactPointBo;

import java.util.Collections;
import java.util.List;

public interface WorkaroundMapper {
    default List<Address> mapAddress(AddressBo value) { return Collections.singletonList(map(value)); }
    Address map(AddressBo value);

    default AddressBo mapAddress(List<Address> value) { return map(value.get(0)); }
    AddressBo map(Address value);


    default List<ContactPoint> mapContactPoint(ContactPointBo value) { return Collections.singletonList(map(value)); }
    ContactPoint map(ContactPointBo value);

    default ContactPointBo mapContactPoint(List<ContactPoint> value) { return map(value.get(0)); }
    ContactPointBo map(ContactPoint value);
}
