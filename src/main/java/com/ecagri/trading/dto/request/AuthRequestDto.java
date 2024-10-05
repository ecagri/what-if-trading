package com.ecagri.trading.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "Authentication Request", description = "Data Transfer Object (DTO) for accesing an account.")
public class AuthRequestDto {

    @Schema(description = "Identify number of the account owner. ", example = "12345678910")
    @NotNull(message = "Account owner id is required")
    @Min(value = 10000000000L, message = "Account owner ID must be at least 11 digits")
    @Max(value = 99999999999L, message = "Account owner ID must be at most 11 digits")
    private Long accountOwnerId;

    @Schema(description = "Password for the account owner's access.", example = "SecurePass123!")
    @NotBlank(message = "Account owner password is required.")
    private String accountOwnerPassword;

}
