package com.budget.budgetapi.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.budget.budgetapi.domain.model.enums.TypePaymentMethod;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PaymentMethod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TypePaymentMethod type;

    @Column(nullable = false)
    private Boolean inactive = Boolean.FALSE;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Company company;

    public void activate() {
        setInactive(false);
    }

    public void inactivate() {
        setInactive(true);
    }

}
