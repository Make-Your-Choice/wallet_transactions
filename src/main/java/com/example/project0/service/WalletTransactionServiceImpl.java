package com.example.project0.service;

import com.example.project0.TransactionType;
import com.example.project0.dto.RequestDto;
import com.example.project0.dto.WalletDto;
import com.example.project0.dto.WalletTransactionDto;
import com.example.project0.entity.WalletTransaction;
import com.example.project0.repository.WalletTransactionRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.math3.random.RandomDataGenerator;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class WalletTransactionServiceImpl implements WalletTransactionService {
    private final WalletTransactionRepository walletTransactionRepository;
    private final ModelMapper modelMapper = new ModelMapper();
    @Override
    public WalletTransactionDto save(WalletTransactionDto walletTransactionDto) {
        if(walletTransactionDto.getIdWalletTransaction() == null) {
            walletTransactionDto.setIdWalletTransaction(new RandomDataGenerator().nextLong(0L, 999L));
        }
        if(walletTransactionDto.getAmount() == null) {
            walletTransactionDto.setAmount(0L);
        }
        WalletTransaction entity = modelMapper.map(walletTransactionDto, WalletTransaction.class);
        walletTransactionRepository.save(entity);
        return walletTransactionDto;
    }

    @Override
    public void deleteAll() {
        walletTransactionRepository.deleteAll();
    }

    @Override
    public void delete(Long id) {
        if(walletTransactionRepository.findById(id).isPresent()) {
            walletTransactionRepository.deleteById(id);
        }
    }

    @Override
    public WalletTransactionDto update(WalletTransactionDto walletTransactionDto) {
        Optional<WalletTransactionDto> dto = get(walletTransactionDto.getIdWalletTransaction());
        if(dto.isPresent()) {
            save(walletTransactionDto);
        }
        return walletTransactionDto;
    }

    @Override
    public List<WalletTransactionDto> getAll() {
        List<WalletTransaction> walletTransactions = walletTransactionRepository.findAll();
        List<WalletTransactionDto> walletTransactionDtos = new ArrayList<>();
        for(WalletTransaction walletTransaction: walletTransactions) {
            WalletTransactionDto dto = modelMapper.map(walletTransaction, WalletTransactionDto.class);
            walletTransactionDtos.add(dto);
        }
        return walletTransactionDtos;
    }

    @Override
    public Optional<WalletTransactionDto> get(Long id) {
        Optional<WalletTransaction> walletTransactionEntity = walletTransactionRepository.findById(id);
        if(walletTransactionEntity.isPresent()) {
            WalletTransaction walletTransaction = walletTransactionEntity.get();
            WalletTransactionDto dto = modelMapper.map(walletTransaction, WalletTransactionDto.class);
            return Optional.ofNullable(dto);
        }
        return Optional.empty();
    }

    @Override
    public WalletTransactionDto makeTransaction(RequestDto requestDto, WalletDto walletDto) {
        WalletTransactionDto transactionDto = WalletTransactionDto
                .builder()
                .idWalletTransaction(new RandomDataGenerator().nextLong(0L, 999L))
//                .wallet(walletDto)
                .transactionType(TransactionType.valueOf(requestDto.getTransactionType()))
                .amount(requestDto.getAmount())
                .build();
        WalletTransaction entity = modelMapper.map(transactionDto, WalletTransaction.class);
        walletTransactionRepository.save(entity);
        return transactionDto;
    }
}
