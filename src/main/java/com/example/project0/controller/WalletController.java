package com.example.project0.controller;

import com.example.project0.dto.RequestDto;
import com.example.project0.dto.WalletDto;
import com.example.project0.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/wallet")
public class WalletController {
    private final WalletService walletService;


    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WalletDto save(@RequestBody WalletDto dto) {
        return walletService.save(dto);
    }

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public List<WalletDto> get() {
        return walletService.getAll();
    }

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            path="/get/{id}"
    )
    public WalletDto getOne(@PathVariable Long id) {
        Optional<WalletDto> dto = walletService.get(id);
        return dto.orElse(null);
    }

    @DeleteMapping(
            path = "/{id}"
    )
    public void delete(@PathVariable Long id) {
        walletService.delete(id);
    }

    @DeleteMapping(
            path = "/delete"
    )
    public void deleteAll() {
        walletService.deleteAll();
    }

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE,
            path = "/make"

    )
    public void makeTransaction(@RequestBody RequestDto requestDto) {
        walletService.makeTransaction(requestDto);
    }
}
