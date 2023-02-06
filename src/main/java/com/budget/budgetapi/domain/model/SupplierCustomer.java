package com.budget.budgetapi.domain.model;

import java.time.OffsetDateTime;
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

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.budget.budgetapi.domain.model.enums.TypeSupplierCustomer;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class SupplierCustomer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TypeSupplierCustomer type;

    private String cnpj;

    private String cpf;

    @CreationTimestamp
    @Column(nullable = false, columnDefinition = "datetime")
    private OffsetDateTime creationDate;

    @UpdateTimestamp
    @Column(nullable = false, columnDefinition = "datetime")
    private OffsetDateTime lastUpdateDate;

    @Column(nullable = false)
    private Boolean inactive = Boolean.FALSE;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Company company;

    @OneToMany(mappedBy = "supplierCustomer")
    private List<AddressSupplierCustomer> addresses;

    @OneToMany(mappedBy = "supplierCustomer")
    private List<ContactSupplierCustomer> contacts;

    public void activate() {
        setInactive(false);
    }

    public void inactivate() {
        setInactive(true);
    }

}
