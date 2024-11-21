package com.project.shopapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import lombok.*;


@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class OrderDetailDTO {
    @JsonProperty("order_id")
    @Min(value=1, message = "Order's ID must be > 0")
    private long orderId;

    @JsonProperty("product_id")
    @Min(value=1, message = "Product's ID must be > 0")
    private long productId;
    @Min(value=0, message = "Product's ID must be >= 0")
    private long price;

    @JsonProperty("number_of_products")
    @Min(value=1, message = "number_of_products must be >= 1")
    private int numberOfProducts;

    @JsonProperty("total_money")
    @Min(value=0, message = "number_of_products must be >= 0")
    private int totalMoney;

    private String color;
}
