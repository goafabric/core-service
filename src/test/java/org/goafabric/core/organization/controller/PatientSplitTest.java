package org.goafabric.core.organization.controller;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PatientSplitTest {
    @Test
    public void patientSplit() {
        assertThat(getFamilyName("")).isEqualTo("");
        assertThat(getFamilyName("Burns")).isEqualTo("Burns");
        assertThat(getFamilyName("Burns, Monty")).isEqualTo("Burns");

        assertThat(getGivenName("")).isEqualTo("");
        assertThat(getGivenName("Burns, Monty")).isEqualTo("Monty");
    }

    private String getFamilyName(String name) {
        return name != null ? name.split(",")[0].trim() : "";
    }

    private String getGivenName(String name) {
        if (name == null) {
            return "";
        }
        String[] names = name.split(",");
        return names.length == 2 ? names[1].trim() : "";
    }
}
