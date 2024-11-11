package com.sporta.member.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

// This class represents the Member entity, which will be mapped to the database using JPA.

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "members")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "member_seq")
    @SequenceGenerator(name = "member_seq", sequenceName = "member_seq", allocationSize = 1)
    private Long id;
    
    private String name;
    private String email;
    private String phoneNumber; // For future SMS reminders
    private LocalDate joinDate; // Date when the member joined
    private LocalDate membershipExpiryDate; // Expiry date of the membership
    
    @Column(name = "is_active", columnDefinition = "BOOLEAN")
    private boolean isActive; // Status to indicate if the membership is active
}