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
    @Column(nullable = false)
    private Long residue;
    @JsonManagedReference
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "wallet", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WalletTransaction> walletTransactions;
}
