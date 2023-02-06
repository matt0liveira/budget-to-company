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

import com.budget.budgetapi.domain.model.enums.TypeCategory;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ParentCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TypeCategory type;

    @Column(nullable = false)
    private Boolean inactive = Boolean.FALSE;

    @Column(nullable = false, columnDefinition = "datetime")
    @CreationTimestamp
    private OffsetDateTime creationDate;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Company company;

    @OneToMany(mappedBy = "parentCategory")
    private List<ChildCategory> childCategories;

    public void activate() {
        setInactive(false);
    }

    public void inactivate() {
        setInactive(true);
    }
}
