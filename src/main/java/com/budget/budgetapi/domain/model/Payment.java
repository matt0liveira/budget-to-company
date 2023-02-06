package com.budget.budgetapi.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false)
    private BigDecimal value;

    @CreationTimestamp
    @Column(columnDefinition = "datetime")
    private OffsetDateTime paymentDate;

    @Column(nullable = false)
    private String installmentsPaid;

    @ManyToOne
    private User user;

    @OneToOne
    private PaymentMethod paymentMethod;

    @ManyToOne
    private Account account;

    @ManyToOne
    private Transaction transaction;

}
