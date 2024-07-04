package com.example.project0.controller;

import com.example.project0.dto.RequestDto;
import com.example.project0.dto.WalletDto;
import com.example.project0.service.WalletService;
import com.example.project0.service.WalletTransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/wallet")
public class WalletController {
    /**
     * Сервис счетов
     */
    private final WalletService walletService;
    /**
     * Сервис транзакций
     */
    private final WalletTransactionService walletTransactionService;

    /**
     * Сохранение счета
     */
    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WalletDto save(@RequestBody WalletDto dto) {
        return walletService.save(dto);
    }
    /**
     * Получение всех счетов
     */
    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<WalletDto> get() {
        return walletService.getAll();
    }
    /**
     * Удаление счета
     */
    @DeleteMapping(
            path = "/{id}"
    )
    public void delete(@PathVariable Long id) {
        walletService.delete(id);
    }
    /**
     * Удаление всех счетов
     */
    @DeleteMapping(
            path = "/delete"
    )
    public void deleteAll() {
        walletService.deleteAll();
    }
    /**
     * Совершение транзакции
     */
    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE,
            path = "/make"

    )
    public void makeTransaction(@RequestBody RequestDto requestDto) {
        Optional<WalletDto> dto = walletService.makeTransaction(requestDto);
        dto.ifPresent(walletDto -> walletTransactionService.makeTransaction(requestDto, walletDto));
    }
}
