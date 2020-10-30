package com.pmanager.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.pmanager.web.rest.TestUtil;

public class RecordItemTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RecordItem.class);
        RecordItem recordItem1 = new RecordItem();
        recordItem1.setId(1L);
        RecordItem recordItem2 = new RecordItem();
        recordItem2.setId(recordItem1.getId());
        assertThat(recordItem1).isEqualTo(recordItem2);
        recordItem2.setId(2L);
        assertThat(recordItem1).isNotEqualTo(recordItem2);
        recordItem1.setId(null);
        assertThat(recordItem1).isNotEqualTo(recordItem2);
    }
}
