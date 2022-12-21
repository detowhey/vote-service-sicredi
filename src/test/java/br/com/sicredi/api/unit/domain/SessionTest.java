package br.com.sicredi.api.unit.domain;

import br.com.sicredi.api.data_provider.FakeData;
import br.com.sicredi.api.domain.Session;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class SessionTest {

    private final FakeData fakeData = FakeData.getInstance();

    @DisplayName("Must set default time to 1 minute")
    @Test
    public void setDefaultTimeout() {
        assertThat(new Session(null).getCreationDate()).isBefore(LocalDateTime.now().plusMinutes(1L));
    }


}
