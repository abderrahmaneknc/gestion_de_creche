package com.creche.security.child;

import com.creche.security.user.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "children")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Child {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    private LocalDate dateOfBirth;
    private String allergies;
    private String specialNeeds;
    private boolean admitted;

    @ManyToOne
    @JoinColumn(name = "parent_user_id", nullable = false)
    @JsonBackReference
    private User parent;
}
