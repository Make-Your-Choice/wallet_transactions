package com.example.project0.controller;

import com.example.project0.dto.WalletTransactionDto;
import com.example.project0.service.WalletTransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/wallet-transaction")
public class WalletTransactionController {
    private final WalletTransactionService walletTransactionService;

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WalletTransactionDto save(@RequestBody WalletTransactionDto dto) {
        return walletTransactionService.save(dto);
    }
    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<WalletTransactionDto> get() {
        return walletTransactionService.getAll();
    }
    @DeleteMapping(
            path = "/{id}"
    )
    public void delete(@PathVariable Long id) {
        walletTransactionService.delete(id);
    }
    @DeleteMapping(
            path = "/delete"
    )
    public void deleteAll() {
        walletTransactionService.deleteAll();
    }
}
