package com.ecagri.trading.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "Account Response", description = "Data Transfer Object (DTO) for retrieving account details.")
public class AccountResponseDto {

    @Schema(description = "Account ID.", example = "123456")
    private Long accountId;

    @Schema(description = "Full name of the account owner.", example = "Çağrı Çaycı")
    private String accountOwnerFullName;

    @Schema(description = "Identify number of the account owner. ", example = "12345678910")
    private Long accountOwnerId;

    @Schema(description = "Portfolios information of the account.")
    private List<PortfolioResponseDto> portfolios;

}