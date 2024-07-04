package com.example.project0.entity;

import com.example.project0.TransactionType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "wallet_transaction")
public class WalletTransaction {
    @Id
    @Column(name = "id_wallet_transaction", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idWalletTransaction;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_wallet")
    @JsonBackReference
    private Wallet wallet;
    @Column(name = "transaction_type")
    @Enumerated
    private TransactionType transactionType;
    @Column(nullable = false)
    private Long amount;
}
