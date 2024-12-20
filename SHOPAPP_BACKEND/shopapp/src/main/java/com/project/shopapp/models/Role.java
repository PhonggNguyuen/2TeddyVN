package com.project.shopapp.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "roles")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Role{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 100)
    private String name;
    public static String USER = "USER";
    public static String ADMIN = "ADMIN";

}
