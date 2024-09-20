package com.ecagri.trading.controller;

import com.ecagri.trading.dto.PortfolioRequestDto;
import com.ecagri.trading.dto.PortfolioResponseDto;
import com.ecagri.trading.service.PortfolioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("api/v1/portfolios")
@Tag(name= "Portfolio controller", description = "Operations on portfolios.")
public class PortfolioController {
    PortfolioService portfolioService;

    @Autowired
    public PortfolioController(PortfolioService portfolioService) {
        this.portfolioService = portfolioService;
    }

    @Operation(summary = "Gel all portfolios.",
                description = "Retrieve all portfolios for all accounts, or for a specific account by specifying the account ID."
    )
    @GetMapping
    public ResponseEntity<List<PortfolioResponseDto>> getPortfolios(@RequestParam(required = false) Long account_id) {
        if(account_id == null){
            return new ResponseEntity<>(portfolioService.getPortfolios(), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(portfolioService.getPortfolios(account_id), HttpStatus.OK);
        }
    }

    @Operation(summary = "Create a portfolio for a specific account.",
                description = "Create a new portfolio. The response contains created portfolio information."
    )
    @PostMapping(path="/{account_id}")
    public ResponseEntity<PortfolioResponseDto> createPortfolio(@PathVariable Long account_id, @Valid @RequestBody PortfolioRequestDto portfolioDto) {
        return new ResponseEntity<>(portfolioService.createPortfolio(account_id, portfolioDto), HttpStatus.CREATED);
    }

    @Operation(summary = "Get a portfolio",
                description = "Access portfolio information."
    )
    @GetMapping(path="/{portfolio_id}")
    public ResponseEntity<PortfolioResponseDto> getPortfolio(@PathVariable Long portfolio_id) {
        return new ResponseEntity<>(portfolioService.getPortfolio(portfolio_id), HttpStatus.OK);
    }

    @Operation(summary = "Update a portfolio",
                description = "Update portfolios' name and balance."
    )
    @PutMapping(path="/{portfolio_id}")
    public ResponseEntity<PortfolioResponseDto> updatePortfolio(@PathVariable Long portfolio_id, @RequestParam(required = false) String portfolio_name, @RequestParam(required = false) BigDecimal balance) {
        return null;
    }

    @Operation(summary = "Delete a portfolio",
                description = "Delete a specific portfolio, including all associated transactions and assets."
    )
    @DeleteMapping(path = "/{portfolio_id}")
    public ResponseEntity<String> deletePortfolio(@PathVariable Long portfolio_id) {
        portfolioService.deletePortfolio(portfolio_id);
        return new ResponseEntity<>("Portfolio is deleted.", HttpStatus.OK);
    }
}
