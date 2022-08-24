package com.game;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.game.controller.PlayController;
import com.game.model.CalculationDto;
import com.game.model.PlaySessionDTO;
import com.game.model.Player;
import com.game.model.StepCalculationRequest;
import com.game.service.PlayService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PlayController.class)
class PlayControllerApiTest {

    private String baseUrl = "/api/v1/game";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PlayService service;

    @Test
    public void createSessionSuccess() throws Exception {

        PlaySessionDTO playSessionDTO = new PlaySessionDTO();
        playSessionDTO.setPlayers(Arrays.asList(new Player("4545")));
        when(service.createSession(any())).thenReturn(playSessionDTO);

        this.mockMvc.perform(get("/api/v1/game/getSession/{sessionId}", "3434"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void calculateStepSuccess() throws Exception {

        CalculationDto calculationDto = new CalculationDto();
        calculationDto.setPlayer(new Player("4545"));
        when(service.calculateStep(any(), any())).thenReturn(calculationDto);

        StepCalculationRequest stepCalculationRequest = new StepCalculationRequest();
        stepCalculationRequest.setDice1(5);
        stepCalculationRequest.setDice2(2);
        stepCalculationRequest.setPlayerUuid("45454545");

        this.mockMvc.perform(post("/api/v1/game/calculateStep/{sessionId}", "3434")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(stepCalculationRequest)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}