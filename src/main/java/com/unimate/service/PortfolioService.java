package com.unimate.service;

import com.unimate.dto.PortfolioRequestDTO;
import com.unimate.dto.PortfolioResponseDTO;
import com.unimate.exception.DuplicateResourceException;
import com.unimate.exception.ResourceNotFoundException;
import com.unimate.exception.UnauthorizedActionException;
import com.unimate.model.Portfolio;
import com.unimate.model.Student;
import com.unimate.repo.PortfolioRepo;
import com.unimate.repo.StudentRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;

@Service
@RequiredArgsConstructor
public class PortfolioService {

    private final PortfolioRepo portfolioRepo;
    private final StudentRepo studentRepo;

    @Transactional
    public PortfolioResponseDTO createPortfolio(Integer studentId, PortfolioRequestDTO dto) {
        Student student = studentRepo.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found: " + studentId));

        if (portfolioRepo.findByStudent_Id(studentId).isPresent()) {
            throw new DuplicateResourceException("Portfolio already exists for this student — use update instead");
        }

        Portfolio portfolio = new Portfolio();
        portfolio.setStudent(student);
        portfolio.setSkills(dto.getSkills() == null ? new HashSet<>() : dto.getSkills());
        portfolio.setAttachments(dto.getAttachments() == null ? new HashSet<>() : dto.getAttachments());
        portfolio.setProjectLinks(dto.getProjectLinks() == null ? new HashSet<>() : dto.getProjectLinks());
        portfolio.setCreatedDate(LocalDateTime.now());
        portfolio.setLastUpdated(LocalDateTime.now());

        Portfolio saved = portfolioRepo.save(portfolio);
        return toResponseDTO(saved);
    }

    @Transactional(readOnly = true)
    public PortfolioResponseDTO getPortfolio(Integer studentId) {
        Portfolio portfolio = portfolioRepo.findByStudent_Id(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Portfolio not found for student: " + studentId));
        return toResponseDTO(portfolio);
    }

    @Transactional
    public PortfolioResponseDTO updatePortfolio(Integer studentId, Integer requesterId, PortfolioRequestDTO dto) {
        if (!studentId.equals(requesterId)) {
            throw new UnauthorizedActionException("You can only update your own portfolio");
        }

        Portfolio portfolio = portfolioRepo.findByStudent_Id(studentId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Portfolio not found for student: " + studentId + " — create one first"));

        portfolio.setSkills(dto.getSkills() == null ? new HashSet<>() : dto.getSkills());
        portfolio.setAttachments(dto.getAttachments() == null ? new HashSet<>() : dto.getAttachments());
        portfolio.setProjectLinks(dto.getProjectLinks() == null ? new HashSet<>() : dto.getProjectLinks());
        portfolio.setLastUpdated(LocalDateTime.now());

        Portfolio saved = portfolioRepo.save(portfolio);
        return toResponseDTO(saved);
    }

    public PortfolioResponseDTO toResponseDTO(Portfolio portfolio) {
        PortfolioResponseDTO dto = new PortfolioResponseDTO();
        dto.setId(portfolio.getId());
        dto.setStudentId(portfolio.getStudent().getId());
        dto.setSkills(portfolio.getSkills());
        dto.setAttachments(portfolio.getAttachments());
        dto.setProjectLinks(portfolio.getProjectLinks());
        dto.setCreatedDate(portfolio.getCreatedDate());
        dto.setLastUpdated(portfolio.getLastUpdated());
        return dto;
    }
}
