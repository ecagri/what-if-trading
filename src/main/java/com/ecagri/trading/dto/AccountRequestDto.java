package com.ecagri.trading.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "Account Request", description = "Data Transfer Object (DTO) for creating an account.")
public class AccountRequestDto {

    @Schema(description = "Full name of the account owner.", example = "Çağrı Çaycı")
    @NotBlank(message = "Account owner full name is required.")
    private String accountOwnerFullName;

    @Schema(description = "Identify number of the account owner. ", example = "12345678910")
    @NotNull(message = "Account owner id is required")
    private Long accountOwnerId;

}
