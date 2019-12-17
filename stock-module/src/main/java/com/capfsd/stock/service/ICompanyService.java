package com.capfsd.stock.service;

import com.capfsd.stock.dto.CompanyDto;
import com.capfsd.stock.dto.PageDto;
import com.capfsd.stock.entity.CompanyEntity;
import com.capfsd.stock.vo.CompanyVo;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ICompanyService {
    void addCompany(CompanyVo companyVo);
    void deleteCompanyById(Long id);
    Integer updateCompany(Long id, CompanyVo companyVo);
    Optional<CompanyDto> findCompanyById(Long id);
    PageDto<CompanyDto> findAll(String keyword, Pageable pageable);
    CompanyDto convertToDto(CompanyEntity companyEntity);
    Optional<CompanyEntity> findById(Long id);
    PageDto<CompanyDto> findAllByStatus(String keyword, Integer status, Pageable pageable);
    Integer updateCompanyByIdWithStatus(Long id, Integer status);
    CompanyDto findDtoByCompanyName(String companyName);
    List<CompanyDto> findByStockExchangeCode(String stockExchangeCode);
}
