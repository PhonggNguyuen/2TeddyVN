package com.project.shopapp.dtos;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.util.Date;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
    @JsonProperty("fullname")
    private String fullName;
    @NotBlank(message = "Phone number is required")
    @JsonProperty("phone_number")
    private String phoneNumber;
    private String address;
    @NotBlank(message = "Password can not be blank")
    private String password;
    @JsonProperty("confirm_password")
    private String confirmPassword;
    @JsonProperty("date_of_birth")
    private Date dateOfBirth;
    @JsonProperty("facebook_account_id")
    private int facebookAccountId;
    @JsonProperty("google_account_id")
    private int googleAccountId;
    @NotNull(message = "Role ID is required")
    @JsonProperty("role_id")
    private Long roleId;

}
