package com.ecagri.trading.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "Account Response", description = "Data Transfer Object (DTO) for retrieving account details.")
public class AccountResponseDto {

    @Schema(description = "Identify number of the account owner. ", example = "12345678910")
    private String accountOwnerId;

    @Schema(description = "Full name of the account owner.", example = "Çağrı Çaycı")
    private String accountOwnerFullName;

    @Schema(description = "Email address of the account owner.", example = "cagri@example.com")
    private String accountOwnerEmail;

    @Schema(description = "Birth date of the account owner.", example = "1990-01-01")
    private LocalDate accountOwnerBirthDate;

    @Schema(description = "LocalDate when the account was created.", example = "2024-10-02")
    private LocalDate accountCreatedDate;

    @Schema(description = "Portfolios information of the account.")
    private List<PortfolioResponseDto> portfolios;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        AccountResponseDto that = (AccountResponseDto) obj;

        return accountOwnerId.equals(that.accountOwnerId);
    }
}