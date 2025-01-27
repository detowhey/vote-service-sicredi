package br.com.sicredi.api.unit.controller;

import br.com.sicredi.api.controller.RulingController;
import br.com.sicredi.api.data_provider.FakeData;
import br.com.sicredi.api.model.Ruling;
import br.com.sicredi.api.dto.request.RulingRequest;
import br.com.sicredi.api.dto.request.SessionRequest;
import br.com.sicredi.api.dto.response.ResultRulingResponse;
import br.com.sicredi.api.service.implementation.RulingService;
import br.com.sicredi.api.stub.RulingStub;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = RulingController.class)
@AutoConfigureMockMvc
public class RulingControllerTest {
    private static final String PATH = "/api/v1/ruling";
    private final FakeData fakeData = FakeData.getInstance();

    @Autowired
    MockMvc mvc;
    @Mock
    RulingService rulingService;

    @Test
    @DisplayName("Must register a new ruling")
    public void registerRuling() {
        String name = fakeData.generateFunnyName();
        String id = fakeData.generatedId();
        RulingRequest rulingRequest = new RulingRequest(name);
        Ruling ruling = Ruling.builder().id(id).name(name).build();
        String content;
        try {
            content = new ObjectMapper().writeValueAsString(rulingRequest);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        when(rulingService.insertTo(Mockito.any())).thenReturn(ruling);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content);
        try {
            mvc.perform(request)
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("id").value(id))
                    .andExpect(jsonPath("name").value(rulingRequest.name()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("Must open a voting session for the ruling")
    public void openRulingSession()  {
        SessionRequest sessaoRequest = new SessionRequest(2);
        String content;
        try {
            content = new ObjectMapper().writeValueAsString(sessaoRequest);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        when(rulingService.openSession(Mockito.any(), Mockito.any())).thenReturn(RulingStub.rulingWithoutSession());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(PATH + "/open/" + fakeData.generatedId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content);
        try {
            mvc.perform(request)
                    .andExpect(status().isOk());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
