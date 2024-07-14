package com.example.project0.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

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
//    private final AtomicLong residue = new AtomicLong(0L);
    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "wallet", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WalletTransaction> walletTransactions;

//    public boolean setResidue(Long residueCurrent, Long residueNew) {
//        return this.residue.compareAndSet(residueCurrent, residueNew);
//    }
//
//    public Long getResidue() {
//        return this.residue.get();
//    }
}
