package com.project.shopapp.dtos;


import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {
    @NotBlank(message = "Category's name can not be blank")
    private String name;


}
