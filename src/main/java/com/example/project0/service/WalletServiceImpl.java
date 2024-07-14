package com.example.project0.service;

import com.example.project0.TransactionType;
import com.example.project0.dto.RequestDto;
import com.example.project0.dto.WalletDto;
import com.example.project0.entity.Wallet;
import com.example.project0.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
@Transactional
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService{
    private final WalletRepository walletRepository;
    private final WalletTransactionService walletTransactionService;
    private final ModelMapper modelMapper = new ModelMapper();
    private final TypeMap<Wallet, WalletDto> propertyMapper = modelMapper.createTypeMap(Wallet.class, WalletDto.class);

    /**
     * Сохранение счета
     * @param walletDto
     * @return
     */
    @Override
    public WalletDto save(WalletDto walletDto) {
//        propertyMapper.addMappings(
//                modelMapper -> modelMapper.map(src -> src.getResidue().get(), WalletDto::setResidue)
//        );
        Wallet entity = modelMapper.map(walletDto, Wallet.class);

        walletRepository.save(entity);
        return walletDto;
    }

    /**
     * Удаление всех счетов
     */
    @Override
    public void deleteAll() {
        walletRepository.deleteAll();
    }

    /**
     * Удаление счета
     * @param id
     */
    @Override
    @Transactional
    public void delete(Long id) {
        if(walletRepository.findById(id).isPresent()) {
            walletRepository.deleteById(id);
        }
    }

    /**
     * Обновление счета
     * @param walletDto
     * @return
     */
    @Override
    public WalletDto update(WalletDto walletDto) {
        Optional<WalletDto> dto = get(walletDto.getIdWallet());
        if(dto.isPresent()) {
            save(walletDto);
        }
        return walletDto;
    }

    /**
     * Получение всех счетов
     * @return
     */
    @Override
    @Transactional
    public List<WalletDto> getAll() {
        List<Wallet> wallets = walletRepository.findAll();
        List<WalletDto> walletDtos = new ArrayList<>();
        for(Wallet wallet: wallets) {
//            propertyMapper.addMappings(
//                    modelMapper -> modelMapper.map(src -> src.getResidue().get(), WalletDto::setResidue)
//            );
            WalletDto dto = modelMapper.map(wallet, WalletDto.class);
            walletDtos.add(dto);
        }
        return walletDtos;
    }

    /**
     * Получение счета
     * @param id
     * @return
     */
    @Override
    public Optional<WalletDto> get(Long id) {
        Optional<Wallet> walletEntity = walletRepository.findById(id);
        if(walletEntity.isPresent()) {
            Wallet wallet = walletEntity.get();
//            propertyMapper.addMappings(
//                    modelMapper -> modelMapper.map(src -> src.getResidue().get(), WalletDto::setResidue)
//            );
            WalletDto dto = modelMapper.map(wallet, WalletDto.class);
            return Optional.ofNullable(dto);
        }
        return Optional.empty();
    }

    /**
     * Совешение транзакции
     * @param requestDto
     * @return
     * @throws InvalidParameterException
     */
    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void makeTransaction(RequestDto requestDto) throws InvalidParameterException {
        Optional<Wallet> walletEntity = walletRepository.findById(requestDto.getIdWallet());
        if(walletEntity.isPresent()) {
            Wallet wallet = walletEntity.get();
            if(TransactionType.valueOf(requestDto.getTransactionType()) == TransactionType.WITHDRAW) {
                if(wallet.getResidue() < requestDto.getAmount()) {
                    throw new InvalidParameterException("Insufficient funds");
                } else {
                    AtomicLong resultResidue = new AtomicLong(wallet.getResidue());
                    if(resultResidue.compareAndSet(
                            wallet.getResidue(), wallet.getResidue() - requestDto.getAmount())) {
                        wallet.setResidue(resultResidue.get());
                        WalletDto dto = modelMapper.map(wallet, WalletDto.class);
                        walletTransactionService.makeTransaction(requestDto, dto);
                    }
                }
            } else if (TransactionType.valueOf(requestDto.getTransactionType()) == TransactionType.DEPOSIT) {

                AtomicLong resultResidue = new AtomicLong(wallet.getResidue());
                if(resultResidue.compareAndSet(
                        wallet.getResidue(), wallet.getResidue() + requestDto.getAmount())) {
                    wallet.setResidue(resultResidue.get());
                    WalletDto dto = modelMapper.map(wallet, WalletDto.class);
                    walletTransactionService.makeTransaction(requestDto, dto);
                }
            }
        } else {
            throw new InvalidParameterException("Wallet does not exist");
        }
    }
}
