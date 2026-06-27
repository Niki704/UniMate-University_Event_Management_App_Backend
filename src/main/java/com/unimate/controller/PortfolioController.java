package com.unimate.controller;

import com.unimate.dto.PortfolioRequestDTO;
import com.unimate.dto.PortfolioResponseDTO;
import com.unimate.security.UserPrincipal;
import com.unimate.service.PortfolioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/portfolios")
@RequiredArgsConstructor
public class PortfolioController {

    private final PortfolioService portfolioService;

    @PostMapping
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<PortfolioResponseDTO> create(@AuthenticationPrincipal UserPrincipal student,
            @Valid @RequestBody PortfolioRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(portfolioService.createPortfolio(student.getId(), dto));
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<PortfolioResponseDTO> getByStudent(@PathVariable Integer studentId) {
        return ResponseEntity.ok(portfolioService.getPortfolio(studentId));
    }

    @PutMapping("/{studentId}")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<PortfolioResponseDTO> update(@PathVariable Integer studentId,
            @AuthenticationPrincipal UserPrincipal requester,
            @Valid @RequestBody PortfolioRequestDTO dto) {
        return ResponseEntity.ok(portfolioService.updatePortfolio(studentId, requester.getId(), dto));
    }
}
