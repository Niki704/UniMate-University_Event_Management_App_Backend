package com.unimate.service;

import com.unimate.dto.PortfolioRequestDTO;
import com.unimate.dto.PortfolioResponseDTO;
import com.unimate.model.Portfolio;
import com.unimate.repo.PortfolioRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
public class PortfolioService {

    @Autowired
    private PortfolioRepo portfolioRepo;

    @Autowired
    private ModelMapper modelMapper;

    public PortfolioResponseDTO savePortfolio(PortfolioRequestDTO portfolioRequestDTO) {
        Portfolio portfolio = modelMapper.map(portfolioRequestDTO, Portfolio.class);

        portfolio.setCreatedDate(LocalDateTime.now());
        portfolio.setLastUpdated(LocalDateTime.now());

        Portfolio savedPortfolio = portfolioRepo.save(portfolio);
        return modelMapper.map(savedPortfolio, PortfolioResponseDTO.class);
    }

    public PortfolioResponseDTO getPortfolioById(Integer userID) {
        Portfolio portfolio = portfolioRepo.findPortfolioById(userID)
                .orElseThrow(() -> new RuntimeException("Portfolio with ID " + userID + " not found"));
        return modelMapper.map(portfolio, PortfolioResponseDTO.class);
    }

    public PortfolioResponseDTO updatePortfolio(Integer userID, PortfolioRequestDTO portfolioRequestDTO) {
        Portfolio portfolio = portfolioRepo.findById(userID)
                .orElseThrow(() -> new RuntimeException("Portfolio with User ID " + userID + " not found"));

        modelMapper.map(portfolioRequestDTO, portfolio);
        portfolio.setLastUpdated(LocalDateTime.now());

        Portfolio updatedPortfolio = portfolioRepo.save(portfolio);
        return modelMapper.map(updatedPortfolio, PortfolioResponseDTO.class);
    }
}
