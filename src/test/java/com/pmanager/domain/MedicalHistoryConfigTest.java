package com.pmanager.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.pmanager.web.rest.TestUtil;

public class MedicalHistoryConfigTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MedicalHistoryConfig.class);
        MedicalHistoryConfig medicalHistoryConfig1 = new MedicalHistoryConfig();
        medicalHistoryConfig1.setId(1L);
        MedicalHistoryConfig medicalHistoryConfig2 = new MedicalHistoryConfig();
        medicalHistoryConfig2.setId(medicalHistoryConfig1.getId());
        assertThat(medicalHistoryConfig1).isEqualTo(medicalHistoryConfig2);
        medicalHistoryConfig2.setId(2L);
        assertThat(medicalHistoryConfig1).isNotEqualTo(medicalHistoryConfig2);
        medicalHistoryConfig1.setId(null);
        assertThat(medicalHistoryConfig1).isNotEqualTo(medicalHistoryConfig2);
    }
}
