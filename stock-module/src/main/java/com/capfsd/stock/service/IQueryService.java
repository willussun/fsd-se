package com.capfsd.stock.service;

import com.capfsd.stock.dto.CompositeCompanyDto;

import java.util.List;

public interface IQueryService {
    List<CompositeCompanyDto> findByStockExchangeCode(String stockExchangeCode);
    List<CompositeCompanyDto> findByCompanyCodeAndStockExchangeCode(String stockExchangeCode, String companyCode);
}