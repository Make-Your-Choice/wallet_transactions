package com.example.project0.controller;

import com.example.project0.configuration.JacksonConfiguration;
import com.example.project0.dto.WalletDto;
import com.example.project0.service.WalletServiceImpl;
import com.example.project0.service.WalletTransactionServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(WalletController.class)
public class WalletControllerTest {
    /**
     * Путь по умолчанию
     */
    private static final String BASE_PATH = "/api/v1/wallet";
    /**
     * Маппер
     */
    private final ObjectMapper mapper = new JacksonConfiguration().objectMapper();
    /**
     * Имитация контроллера
     */
    @Autowired
    private MockMvc mockMvc;
    /**
     * Сервис счетов
     */
    @MockBean
    private WalletServiceImpl walletService;
    /**
     * Сервис транзакций
     */
    @MockBean
    private WalletTransactionServiceImpl walletTransactionService;

    /**
     * Тест на сохранение счета
     *
     */
    @Test
    public void saveTest() throws Exception {
        WalletDto dto = new WalletDto(1L, 500L, new ArrayList<>());
        when(walletService.save(dto)).thenReturn(dto);
        mockMvc.perform(postAction(BASE_PATH, dto)).andExpect(status().isOk());
    }

    /**
     * Тест на получение счета
     *
     */
    @Test
    public void getTest() throws Exception {
        WalletDto dto = new WalletDto(1L, 500L, new ArrayList<>());
        when(walletService.get(1L)).thenReturn(Optional.of(dto));
        mockMvc.perform(getAction(BASE_PATH, dto)).andExpect(status().isOk());
    }

    /**
     * Тест на удаление счета
     *
     */
    @Test
    public void deleteTest() throws Exception {
        Long id = 1L;
        doNothing().when(walletService).delete(id);
        mockMvc.perform(deleteAction(BASE_PATH + "/1", id)).andExpect(status().isOk());
    }

    /**
     * Тест на удаление всех счетов
     *
     */
    @Test
    public void deleteAllTest() throws Exception {
        doNothing().when(walletService).deleteAll();
        mockMvc.perform(deleteAction(BASE_PATH + "/delete", null)).andExpect(status().isOk());
    }

    private MockHttpServletRequestBuilder postAction(String uri, Object dto) throws JsonProcessingException {
        return post(uri)
                .contentType(APPLICATION_JSON)
                .content(mapper.writeValueAsString(dto));
    }

    private MockHttpServletRequestBuilder getAction(String uri, Object dto) throws JsonProcessingException {
        return get(uri)
                .contentType(APPLICATION_JSON)
                .content(mapper.writeValueAsString(dto));
    }

    private MockHttpServletRequestBuilder deleteAction(String uri, Object dto) throws JsonProcessingException {
        return delete(uri)
                .contentType(APPLICATION_JSON)
                .content(mapper.writeValueAsString(dto));
    }
}
