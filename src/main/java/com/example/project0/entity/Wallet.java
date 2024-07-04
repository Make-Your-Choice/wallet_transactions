package com.example.project0.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "wallet")
public class Wallet {
    @Id
    @Column(name = "id_wallet", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idWallet;
//    @Column(precision = 15, scale = 12, nullable = false)
    @Column(nullable = false)
    private Long residue;
    @JsonManagedReference
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "wallet", cascade = CascadeType.ALL, orphanRemoval = true)
//    @JoinColumn(name = "id_wallet_transaction")
    private List<WalletTransaction> walletTransactions;
}
