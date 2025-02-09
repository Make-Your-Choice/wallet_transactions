package com.example.project0.dto;

import lombok.*;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RequestDto {
    @NonNull
    private Long idWallet;
    @NonNull
    private String transactionType;
    @NonNull
    private Long amount;
}
