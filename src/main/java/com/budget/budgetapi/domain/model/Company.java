package com.budget.budgetapi.domain.model;

import java.time.OffsetDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Company {
    
    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String cnpj;

    @CreationTimestamp
    @Column(nullable = false, columnDefinition = "datetime")
    private OffsetDateTime creationDate;

    @UpdateTimestamp
    @Column(nullable = false, columnDefinition = "datetime")
    private OffsetDateTime lastUpdateDate;

    @OneToMany(mappedBy = "company")
    private List<AddressCompany> addresses;

    @OneToMany(mappedBy = "company")
    private List<ParentCategory> parentCategories;

    @OneToMany(mappedBy = "company")
    private List<ChildCategory> childCategories;

    @OneToMany(mappedBy = "company")
    private List<Account> accounts;

    @OneToMany(mappedBy = "company")
    private List<User> users;

    @OneToMany(mappedBy = "company")
    private List<PaymentMethod> paymentMethods;

    @OneToMany(mappedBy = "company")
    private List<SupplierCustomer> suppliersCustomers;

}
