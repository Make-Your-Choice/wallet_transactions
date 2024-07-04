package com.example.project0.service;

import com.example.project0.dto.RequestDto;
import com.example.project0.dto.WalletDto;
import com.example.project0.dto.WalletTransactionDto;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface WalletService {
    WalletDto save(WalletDto walletDto);
    void deleteAll();
    void delete(Long id);
    WalletDto update (WalletDto walletDto);
    List<WalletDto> getAll();
    Optional<WalletDto> get(Long id);

    Optional<WalletDto> makeTransaction(RequestDto requestDto) throws InvalidParameterException;
}
