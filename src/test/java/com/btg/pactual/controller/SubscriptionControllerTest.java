package com.btg.pactual.controller;

import com.btg.pactual.service.subscription.ISubscriptionService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SubscriptionController.class)
class SubscriptionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ISubscriptionService subscriptionService;

    @Test
    @DisplayName("POST /suscripciones/subscribe - Debe retornar éxito")
    void testSubscribe() throws Exception {
        Long clientId = 1L;
        Long fundId = 2L;
        String expectedMessage = "Successfully subscribed to fund FIC1";

        when(subscriptionService.subscribe(clientId, fundId)).thenReturn(expectedMessage);

        mockMvc.perform(post("/suscripciones/subscribe")
                        .param("clientId", String.valueOf(clientId))
                        .param("fundId", String.valueOf(fundId)))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedMessage));
    }

    @Test
    @DisplayName("POST /suscripciones/cancel - Debe cancelar suscripción")
    void testCancelSubscription() throws Exception {
        Long clientId = 1L;
        Long fundId = 2L;
        String expectedMessage = "Successfully cancelled subscription to fund FIC1";

        when(subscriptionService.cancelSubscription(clientId, fundId)).thenReturn(expectedMessage);

        mockMvc.perform(post("/suscripciones/cancel")
                        .param("clientId", String.valueOf(clientId))
                        .param("fundId", String.valueOf(fundId)))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedMessage));
    }

    @Test
    @DisplayName("GET /suscripciones/history - Debe retornar historial vacío")
    void testTransactionHistoryEmpty() throws Exception {
        Long clientId = 1L;

        when(subscriptionService.getTransactionHistory(clientId)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/suscripciones/history")
                        .param("clientId", String.valueOf(clientId))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }
}
