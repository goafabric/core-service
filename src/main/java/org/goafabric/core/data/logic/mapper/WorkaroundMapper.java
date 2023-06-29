package org.goafabric.core.data.logic.mapper;

import org.goafabric.core.data.controller.vo.Address;
import org.goafabric.core.data.controller.vo.ContactPoint;
import org.goafabric.core.data.repository.entity.AddressEo;
import org.goafabric.core.data.repository.entity.ContactPointEo;

import java.util.Collections;
import java.util.List;

public interface WorkaroundMapper {
    default List<Address> mapAddress(AddressEo value) { return Collections.singletonList(map(value)); }
    Address map(AddressEo value);

    default AddressEo mapAddress(List<Address> value) { return map(value.get(0)); }
    AddressEo map(Address value);


    default List<ContactPoint> mapContactPoint(ContactPointEo value) { return Collections.singletonList(map(value)); }
    ContactPoint map(ContactPointEo value);

    default ContactPointEo mapContactPoint(List<ContactPoint> value) { return map(value.get(0)); }
    ContactPointEo map(ContactPoint value);
}
