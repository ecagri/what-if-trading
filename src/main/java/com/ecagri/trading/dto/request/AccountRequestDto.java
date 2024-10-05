package com.ecagri.trading.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "Account Request", description = "Data Transfer Object (DTO) for creating an account.")
public class AccountRequestDto {

    @Schema(description = "Identify number of the account owner. ", example = "12345678910")
    @NotNull(message = "Account owner id is required")
    @Min(value = 10000000000L, message = "Account owner ID must be at least 11 digits")
    @Max(value = 99999999999L, message = "Account owner ID must be at most 11 digits")
    private Long accountOwnerId;

    @Schema(description = "Full name of the account owner.", example = "Çağrı Çaycı")
    @NotBlank(message = "Account owner full name is required.")
    private String accountOwnerFullName;

    @Schema(description = "Password for the account owner's access.", example = "SecurePass123!")
    @NotBlank(message = "Account owner password is required.")
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).{8,}$",
            message = "Password must be at least 8 characters long, contain at least one digit, one lowercase letter, one uppercase letter, and one special character."
    )
    private String accountOwnerPassword;

    @Schema(description = "Email address of the account owner.", example = "cagri@example.com")
    @NotBlank(message = "Account owner email is required.")
    @Email(message = "Email should be valid.")
    private String accountOwnerEmail;

    @Schema(description = "Birth date of the account owner.", example = "1990-01-01")
    @NotNull(message = "Account owner birth date is required.")
    private LocalDate accountOwnerBirthDate;


}
