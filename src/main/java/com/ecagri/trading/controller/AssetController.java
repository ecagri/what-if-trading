package com.ecagri.trading.controller;

import com.ecagri.trading.dto.AssetRequestDto;
import com.ecagri.trading.dto.AssetResponseDto;
import com.ecagri.trading.service.AssetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/assets")
@Tag(name = "Asset Controller", description = "Operations on assets.")
public class AssetController {

    AssetService assetService;

    @Autowired
    public AssetController(AssetService AssetService) {
        this.assetService = AssetService;
    }

    @Operation(summary = "Create a asset for a specific portfolio.",
            description = "Create a new asset. The response contains created asset information."
    )
    @PostMapping("/{portfolio_id}")
    public ResponseEntity<AssetResponseDto> createAsset(@PathVariable Long portfolio_id, @RequestBody AssetRequestDto assetDto) {
        return new ResponseEntity<>(assetService.createAsset(portfolio_id, assetDto), HttpStatus.CREATED);
    }

//    @GetMapping("/by-code/{stock_code}")
//    public ResponseEntity<List<AssetDto>> getAsset(@PathVariable("stock_code") String stock_code) {
//        return new ResponseEntity<>(assetService.getAssetsByCode(stock_code), HttpStatus.OK);
//    }
//
//    @GetMapping("/by-portfolio/{portfolio_id}")
//    public ResponseEntity<List<AssetDto>> getAsset(@PathVariable("portfolio_id") Long portfolio_id) {
//        return new ResponseEntity<>(assetService.getAssetsByPortfolio(portfolio_id), HttpStatus.OK);
//    }

    @Operation(summary = "Get assets: all, portfolio-specific, or stock-specific",
            description = "Search for assets based on specified parameters to retrieve all assets that meet the criteria."
    )
    @GetMapping()
    public ResponseEntity<List<AssetResponseDto>> getAssets(@RequestParam(required=false) String stock_code, @RequestParam(required=false) Long portfolio_id) {
        List<AssetResponseDto> assets;
        if(stock_code != null && portfolio_id != null) {
            List<AssetResponseDto> assetsByCode = assetService.getAssetsByCode(stock_code);
            List<AssetResponseDto> assetsByPortfolio = assetService.getAssetsByPortfolio(portfolio_id);

            assets = new ArrayList<>(assetsByCode);

            assets.retainAll(assetsByPortfolio);
        }
        else if(stock_code != null){
            assets = assetService.getAssetsByCode(stock_code);
        } else if (portfolio_id != null) {
            assets = assetService.getAssetsByPortfolio(portfolio_id);
        }else{
            assets = assetService.getAllAssets();
        }
        return new ResponseEntity<>(assets, HttpStatus.OK);
    }
}
