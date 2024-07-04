package com.example.project0.service;

import com.example.project0.dto.RequestDto;
import com.example.project0.dto.WalletDto;
import com.example.project0.dto.WalletTransactionDto;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface WalletTransactionService {
    WalletTransactionDto save(WalletTransactionDto walletTransactionDto);
    void deleteAll();
    void delete(Long id);
    WalletTransactionDto update(WalletTransactionDto walletTransactionDto);
    List<WalletTransactionDto> getAll();
    Optional<WalletTransactionDto> get(Long id);
    WalletTransactionDto makeTransaction(RequestDto requestDto, WalletDto walletDto);
}
