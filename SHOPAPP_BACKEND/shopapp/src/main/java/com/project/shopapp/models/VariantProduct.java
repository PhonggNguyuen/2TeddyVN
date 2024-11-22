package com.project.shopapp.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "variantproduct")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VariantProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "color", nullable = false)
    private String color; // Màu sắc của biến thể

    @Column(name = "size", nullable = false)
    private String size; // Kích thước của biến thể

    @Column(name = "quantity", nullable = false)
    private Long quantity; // Số lượng tồn kho của biến thể

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product; // Sản phẩm liên kết với biến thể
}
