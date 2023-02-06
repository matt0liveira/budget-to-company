package com.budget.budgetapi.domain.model;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.budget.budgetapi.domain.model.enums.TypeAccount;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private BigDecimal balance;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TypeAccount type;

    @Column(nullable = false)
    private Boolean inactive = Boolean.FALSE;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Company company;

    @OneToMany(mappedBy = "account")
    private List<Transaction> transactions;

    @OneToMany(mappedBy = "account")
    private List<Payment> payments;

    public void activate() {
        setInactive(false);
    }

    public void inactivate() {
        setInactive(true);
    }

}
