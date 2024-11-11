package com.sporta.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

// This class will be used as a data transfer object to move Member data between client and server.

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MemberDTO {
    
    private Long id;
    private String name;
    private String email;
    private String phoneNumber;
    private LocalDate joinDate;
    private LocalDate membershipExpiryDate;
    private boolean isActive;
}
