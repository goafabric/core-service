package org.goafabric.core.ui.mrc.tabs;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.goafabric.core.ui.mrc.tabs.MedicalRecordView.getFamilyName;
import static org.goafabric.core.ui.mrc.tabs.MedicalRecordView.getGivenName;

class MedicalRecordViewTest {
    @Test
    public void patientSplit() {
        assertThat(getFamilyName("")).isEqualTo("");
        assertThat(getFamilyName("Burns")).isEqualTo("Burns");
        assertThat(getFamilyName("Burns, Monty")).isEqualTo("Burns");

        assertThat(getFamilyName(",")).isEqualTo("");

        assertThat(getGivenName("")).isEqualTo("");
        assertThat(getGivenName("Burns, Monty")).isEqualTo("Monty");
    }

}