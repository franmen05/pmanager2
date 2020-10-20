package com.pmanager.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.pmanager.web.rest.TestUtil;

public class TurnTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Turn.class);
        Turn turn1 = new Turn();
        turn1.setId(1L);
        Turn turn2 = new Turn();
        turn2.setId(turn1.getId());
        assertThat(turn1).isEqualTo(turn2);
        turn2.setId(2L);
        assertThat(turn1).isNotEqualTo(turn2);
        turn1.setId(null);
        assertThat(turn1).isNotEqualTo(turn2);
    }
}
