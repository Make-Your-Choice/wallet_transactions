package com.example.project0.dto;

import com.example.project0.TransactionType;
import lombok.*;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class WalletTransactionDto {
    @NonNull
    private Long idWalletTransaction;
    private WalletDto wallet;
    @NonNull
    private TransactionType transactionType;
    @NonNull
    private Long amount;
}
