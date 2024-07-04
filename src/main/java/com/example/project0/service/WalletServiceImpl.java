package com.example.project0.service;

import com.example.project0.TransactionType;
import com.example.project0.dto.RequestDto;
import com.example.project0.dto.WalletDto;
import com.example.project0.entity.Wallet;
import com.example.project0.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.math3.random.RandomDataGenerator;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;
import org.springframework.transaction.annotation.Transactional;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService{
    private final WalletRepository walletRepository;
    private final ModelMapper modelMapper = new ModelMapper();
    @Override
    public WalletDto save(WalletDto walletDto) {
        if(walletDto.getIdWallet() == null) {
            walletDto.setIdWallet(new RandomDataGenerator().nextLong(0L, 999L));
        }
        if(walletDto.getResidue() == null) {
            walletDto.setResidue(0L);
        }
        if(walletDto.getWalletTransactions() == null) {
            walletDto.setWalletTransactions(new ArrayList<>());
        }
        Wallet entity = modelMapper.map(walletDto, Wallet.class);

        walletRepository.save(entity);
        return walletDto;
    }

    @Override
    public void deleteAll() {
        walletRepository.deleteAll();
    }

    @Override
    public void delete(Long id) {
        if(walletRepository.findById(id).isPresent()) {
            walletRepository.deleteById(id);
        }
    }

    @Override
    public WalletDto update(WalletDto walletDto) {
        Optional<WalletDto> dto = get(walletDto.getIdWallet());
        if(dto.isPresent()) {
            save(walletDto);
        }
        return walletDto;
    }

    @Override
    public List<WalletDto> getAll() {
        List<Wallet> wallets = walletRepository.findAll();
        List<WalletDto> walletDtos = new ArrayList<>();
        for(Wallet wallet: wallets) {
            WalletDto dto = modelMapper.map(wallet, WalletDto.class);
            walletDtos.add(dto);
        }
        return walletDtos;
    }

    @Override
    public Optional<WalletDto> get(Long id) {
        Optional<Wallet> walletEntity = walletRepository.findById(id);
        if(walletEntity.isPresent()) {
            Wallet wallet = walletEntity.get();
            WalletDto dto = modelMapper.map(wallet, WalletDto.class);
            return Optional.ofNullable(dto);
        }
        return Optional.empty();
    }

    @Override
    public Optional<WalletDto> makeTransaction(RequestDto requestDto) throws InvalidParameterException {
        Optional<Wallet> walletEntity = walletRepository.findById(requestDto.getIdWallet());
        if(walletEntity.isPresent()) {
            Wallet wallet = walletEntity.get();
            if(TransactionType.valueOf(requestDto.getTransactionType()) == TransactionType.WITHDRAW) {
                if(wallet.getResidue() < requestDto.getAmount()) {
                    //все плохо
                    //throw new InvalidParameterException();
                    System.out.println("Insufficient funds");
                } else {
                    wallet.setResidue(
                            wallet.getResidue() - requestDto.getAmount()
                    );
                    WalletDto dto = modelMapper.map(wallet, WalletDto.class);
                    return Optional.ofNullable(dto);
                }
            } else if (TransactionType.valueOf(requestDto.getTransactionType()) == TransactionType.DEPOSIT) {
                wallet.setResidue(
                        wallet.getResidue() + requestDto.getAmount()
                );
                WalletDto dto = modelMapper.map(wallet, WalletDto.class);
                return Optional.ofNullable(dto);
            } else {
                //иди отсюда
                //throw new InvalidParameterException();
                System.out.println("Incorrect request format");
            }
        } else {
            System.out.println("Wallet does not exist");
        }
        return Optional.empty();
    }
}
