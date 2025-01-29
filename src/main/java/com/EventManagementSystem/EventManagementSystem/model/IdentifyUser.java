package com.EventManagementSystem.EventManagementSystem.model;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Table(name = "Identifyusers")
public class IdentifyUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user; // Reference to the User entity


    private String citizenshipImage; // For storing citizenship image

    private String userImage; // For storing user image


}
