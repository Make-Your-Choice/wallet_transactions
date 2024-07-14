package com.example.project0.service;

import com.example.project0.dto.RequestDto;
import com.example.project0.dto.WalletDto;
import com.example.project0.repository.WalletRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class WalletServiceTest {
    private WalletServiceImpl walletService;
    @Autowired
    private WalletRepository walletRepository;
    @Autowired
    private WalletTransactionServiceImpl walletTransactionService;

    /**
     * Инициализация
     */
    @BeforeEach
    public void before() {
        walletService = new WalletServiceImpl(walletRepository, walletTransactionService);
        walletRepository.deleteAll();
    }

    /**
     * Тест на сохранение и получение счета
     */
    @Test
    public void saveGetTest() {
        WalletDto dto = new WalletDto(1L, 500L, new ArrayList<>());
        WalletDto saved = walletService.save(dto);
        assertNotNull(saved);
        assertEquals(dto.getIdWallet(), saved.getIdWallet());
        assertEquals(dto.getResidue(), saved.getResidue());
    }

    /**
     * Тест на обновление счета
     */
    @Test
    public void updateTest() {
        WalletDto dto = new WalletDto(2L, 500L, new ArrayList<>());
        walletService.save(dto);
        dto.setResidue(600L);
        WalletDto updated = walletService.update(dto);
        assertEquals(dto.getResidue(), updated.getResidue());
    }

    /**
     * Тест на проведение транзакции
     */
    @Test
    public void makeTransactionTest() {
        WalletDto dto = new WalletDto(3L, 500L, new ArrayList<>());
        RequestDto requestDto = new RequestDto(3L, "DEPOSIT", 1000L);
        walletService.makeTransaction(requestDto);
        Optional<WalletDto> updated = walletService.get(3L);
        if(updated.isPresent()) {
            WalletDto upd = updated.get();
            assertEquals(dto.getResidue() + requestDto.getAmount(), upd.getResidue());
        }
    }

    /**
     * Тест на удаление счета
     */
    @Test
    public void deleteTest() {
        WalletDto dto = new WalletDto(4L, 500L, new ArrayList<>());
        walletService.save(dto);
        Long id = walletRepository.findAll().get(0).getIdWallet();
        walletService.delete(id);
        assertEquals(0, walletService.getAll().size());
    }

    /**
     * Тест на удаление всех счетов
     */
    @Test
    public void deleteAllTest() {
        WalletDto dto1 = new WalletDto(5L, 500L, new ArrayList<>());
        WalletDto dto2 = new WalletDto(6L, 200L, new ArrayList<>());
        walletService.save(dto1);
        walletService.save(dto2);
        walletService.deleteAll();
        assertEquals(0, walletService.getAll().size());
    }

    /**
     * Тест на получение списка счетов
     */
    @Test
    public void getAllTest() {
        WalletDto dto1 = new WalletDto(7L, 500L, new ArrayList<>());
        WalletDto dto2 = new WalletDto(8L, 200L, new ArrayList<>());
        walletService.save(dto1);
        walletService.save(dto2);
        List<WalletDto> wallets = walletService.getAll();
        assertEquals(2, wallets.size());
        assertNotNull(wallets.get(0));
        assertNotNull(wallets.get(1));
    }
}
