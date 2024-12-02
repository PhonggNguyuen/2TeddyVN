package com.project.shopapp.models;

import jakarta.persistence.*;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Tự động sinh ID
    private Long id;

    @Column(name = "name", nullable = false) // Ánh xạ với cột "name" trong cơ sở dữ liệu
    private String name;
}
