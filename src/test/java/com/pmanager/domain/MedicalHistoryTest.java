package com.pmanager.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.pmanager.web.rest.TestUtil;

public class MedicalHistoryTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MedicalHistory.class);
        MedicalHistory medicalHistory1 = new MedicalHistory();
        medicalHistory1.setId(1L);
        MedicalHistory medicalHistory2 = new MedicalHistory();
        medicalHistory2.setId(medicalHistory1.getId());
        assertThat(medicalHistory1).isEqualTo(medicalHistory2);
        medicalHistory2.setId(2L);
        assertThat(medicalHistory1).isNotEqualTo(medicalHistory2);
        medicalHistory1.setId(null);
        assertThat(medicalHistory1).isNotEqualTo(medicalHistory2);
    }
}
