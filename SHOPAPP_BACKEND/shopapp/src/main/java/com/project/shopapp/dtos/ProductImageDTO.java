package com.project.shopapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductImageDTO {

    @JsonProperty("product_id")
    @Min(value = 1 , message = "productID must be > 1")
    private Long productId;

    @Size(min = 5, max = 200, message = "Image's name must be between 5 and 200 characters")
    @JsonProperty("image_url")
    private String imageUrl;
}
