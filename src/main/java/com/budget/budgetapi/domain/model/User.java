package com.budget.budgetapi.domain.model;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    private Boolean inactive = Boolean.FALSE;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Company company;

    @CreationTimestamp
    @Column(nullable = false, columnDefinition = "datetime")
    private OffsetDateTime creationDate;

    @ManyToMany
    @JoinTable(name = "user_profile", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "profile_id"))
    private Set<Profile> profiles = new HashSet<>();

    
    @UpdateTimestamp
    private OffsetDateTime lasUpdateDate;

    @OneToMany(mappedBy = "user")
    private List<ContactUser> contactsUser;

    @OneToMany(mappedBy = "user")
    private List<Payment> payments;


    public void connectProfile(Profile profile) {
        getProfiles().add(profile);
    }

    public void disassociateProfile(Profile profile) {
        getProfiles().remove(profile);
    }

    public boolean isNew() {
        return getId() == null;
    }
}
