package com.unimate.controller;

import com.unimate.dto.PortfolioRequestDTO;
import com.unimate.dto.PortfolioResponseDTO;
import com.unimate.service.PortfolioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(value = "api/v6/portfolio/")
public class PortfolioController {

    @Autowired
    private PortfolioService portfolioService;

    @PostMapping("/add")
    public ResponseEntity<PortfolioResponseDTO> savePortfolio(@RequestBody PortfolioRequestDTO portfolioRequestDTO) {
        PortfolioResponseDTO response = portfolioService.savePortfolio(portfolioRequestDTO);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get/{userID}")
    public ResponseEntity<PortfolioResponseDTO> getPortfolioById(@PathVariable Integer userID) {
        PortfolioResponseDTO response = portfolioService.getPortfolioById(userID);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update/{userID}")
    public ResponseEntity<PortfolioResponseDTO> updatePortfolio(@PathVariable Integer userID, @RequestBody PortfolioRequestDTO portfolioRequestDTO) {
        PortfolioResponseDTO response = portfolioService.updatePortfolio(userID, portfolioRequestDTO);
        return ResponseEntity.ok(response);
    }
}
