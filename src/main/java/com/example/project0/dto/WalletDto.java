package com.example.project0.dto;

import lombok.*;

import java.util.List;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class WalletDto {
    @NonNull
    private Long idWallet;
    @NonNull
    private Long residue;
    private List<WalletTransactionDto> walletTransactions;
}
