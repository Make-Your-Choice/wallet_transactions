package com.example.project0.service;

import com.example.project0.TransactionType;
import com.example.project0.dto.RequestDto;
import com.example.project0.dto.WalletDto;
import com.example.project0.dto.WalletTransactionDto;
import com.example.project0.repository.WalletTransactionRepository;
import com.example.project0.service.WalletTransactionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class WalletTransactionServiceTest {
    private WalletTransactionServiceImpl walletTransactionService;
    @Autowired
    private WalletTransactionRepository walletTransactionRepository;

    /**
     * Инициализация
     */
    @BeforeEach
    public void before() {
        walletTransactionService = new WalletTransactionServiceImpl(walletTransactionRepository);
        walletTransactionRepository.deleteAll();
    }

    /**
     * Тест на сохранение и получение транзакции
     */
    @Test
    public void saveGetTest() {
        WalletTransactionDto dto = new WalletTransactionDto(1L, null, TransactionType.DEPOSIT, 500L);
        WalletTransactionDto saved = walletTransactionService.save(dto);
        assertNotNull(saved);
        assertEquals(dto.getIdWalletTransaction(), saved.getIdWalletTransaction());
        assertEquals(dto.getTransactionType(), saved.getTransactionType());
        assertEquals(dto.getAmount(), saved.getAmount());
    }

    /**
     * Тест на обновление транзакции
     */
    @Test
    public void updateTest() {
        WalletTransactionDto dto = new WalletTransactionDto(2L, null, TransactionType.DEPOSIT, 500L);
        walletTransactionService.save(dto);
        dto.setAmount(800L);
        WalletTransactionDto updated = walletTransactionService.update(dto);
        assertEquals(dto.getAmount(), updated.getAmount());
    }

    /**
     * Тест на проведение транзакции
     */
    @Test
    public void makeTransactionTest() {
        WalletDto walletDto = new WalletDto(3L, 500L, new ArrayList<>());
        RequestDto requestDto = new RequestDto(3L, "DEPOSIT", 1000L);
        walletTransactionService.makeTransaction(requestDto, walletDto);
        WalletTransactionDto saved = walletTransactionService.makeTransaction(requestDto, walletDto);
        assertEquals(requestDto.getAmount(), saved.getAmount());
    }

    /**
     * Тест на удаление транзакции
     */
    @Test
    public void deleteTest() {
        WalletTransactionDto dto = new WalletTransactionDto(4L, null, TransactionType.DEPOSIT, 500L);
        walletTransactionService.save(dto);
        Long id = walletTransactionRepository.findAll().get(0).getIdWalletTransaction();
        walletTransactionService.delete(id);
        assertEquals(0, walletTransactionService.getAll().size());
    }

    /**
     * Тест на удаление всех транзакций
     */
    @Test
    public void deleteAllTest() {
        WalletTransactionDto dto1 = new WalletTransactionDto(5L, null, TransactionType.DEPOSIT, 500L);
        WalletTransactionDto dto2 = new WalletTransactionDto(6L, null, TransactionType.WITHDRAW, 400L);
        walletTransactionService.save(dto1);
        walletTransactionService.save(dto2);
        walletTransactionService.deleteAll();
        assertEquals(0, walletTransactionService.getAll().size());
    }

    /**
     * Тест на получение списка транзакций
     */
    @Test
    public void getAllTest() {
        WalletTransactionDto dto1 = new WalletTransactionDto(7L, null, TransactionType.DEPOSIT, 500L);
        WalletTransactionDto dto2 = new WalletTransactionDto(8L, null, TransactionType.WITHDRAW, 400L);
        walletTransactionService.save(dto1);
        walletTransactionService.save(dto2);
        List<WalletTransactionDto> transactions = walletTransactionService.getAll();
        assertEquals(2, transactions.size());
        assertNotNull(transactions.get(0));
        assertNotNull(transactions.get(1));
    }
}
