package com.budget.budgetapi.domain.model;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;

import org.hibernate.annotations.CreationTimestamp;

import com.budget.budgetapi.domain.model.enums.StatusTransaction;
import com.budget.budgetapi.domain.model.enums.TypeTransaction;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private BigDecimal value;

    @Column(nullable = false)
    private Date issueDate;

    @Column(nullable = false)
    private Date competenceDate;

    @Column(nullable = false)
    private Date dueDate;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TypeTransaction type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusTransaction status;

    @Column(nullable = false)
    private Integer intallmentsTotalNumber;

    @Column(nullable = false)
    private Integer intallmentNumber;

    @CreationTimestamp
    private OffsetDateTime creationDate;

    @ManyToOne
    private ChildCategory category;

    @ManyToOne
    private User user;

    @ManyToOne
    private Company company;

    @ManyToOne
    private SupplierCustomer supplierCustomer;

    @ManyToOne
    private PaymentMethod paymentMethod;

    @ManyToOne
    private Account account;

    @OneToMany(mappedBy = "transaction")
    private List<Payment> payments;

    @PrePersist
    private void randomCode() {
        setCode(UUID.randomUUID().toString());
    }

    public boolean isNew() {
        return getId() == null;
    }
}
