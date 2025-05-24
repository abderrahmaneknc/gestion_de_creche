package com.creche.security.child;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChildRequest {
    private Long id;
    private String fullName;
    private String dateOfBirth;
    private String address;
    private String parentContact;
    private String allergies;
    private String specialNeeds;
    private Long parentId; // ou User parent;

}
