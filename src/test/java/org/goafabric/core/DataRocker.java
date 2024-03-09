package org.goafabric.core;

import org.goafabric.core.extensions.TenantContext;
import org.goafabric.core.organization.controller.dto.*;
import org.goafabric.core.organization.controller.dto.types.AddressUse;
import org.goafabric.core.organization.controller.dto.types.ContactPointSystem;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

public class DataRocker {
    public static void setTenantId(String tenantId) {
        TenantContext.setTenantId(tenantId);
    }

    public static Patient createPatient(String givenName, String familyName, List<Address> addresses, List<ContactPoint> contactPoints) {
        return new Patient(null, null, givenName, familyName, "male", LocalDate.of(2020, 1, 8),
                addresses, contactPoints
        );
    }

    public static Practitioner createPractitioner(String givenName, String familyName, List<Address> addresses, List<ContactPoint> contactPoints) {
        return new Practitioner(null, null, givenName, familyName, "male", LocalDate.of(2020, 1, 8),
                "123456667", addresses, contactPoints
        );
    }

    public static Organization createOrganization(String name, List<Address> addresses, List<ContactPoint> contactPoints) {
        return new Organization(null, null, name, "4711", addresses, contactPoints);
    }

    public static List<Address> createAddress(String street) {
        return Collections.singletonList(
                new Address(null, null, AddressUse.HOME.getValue(),street, "Springfield"
                        , "555", "Florida", "US"));
    }

    public static List<ContactPoint> createContactPoint(String phone) {
        return Collections.singletonList(new ContactPoint(null, null, AddressUse.HOME.getValue(), ContactPointSystem.PHONE.getValue(), phone));
    }
}
