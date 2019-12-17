package com.capfsd.stock.service;

import com.capfsd.stock.dto.CompanyDto;
import com.capfsd.stock.dto.PageDto;
import com.capfsd.stock.dto.StockExchangeDto;
import com.capfsd.stock.entity.CompanyEntity;
import com.capfsd.stock.entity.StockExchangeEntity;
import com.capfsd.stock.vo.CompanyVo;
import com.capfsd.stock.vo.StockExchangeVo;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IStockExchangeService {
    void addStockExchange(StockExchangeVo stockExchangeVo);
    void deleteStockExchangeById(Long id);
    StockExchangeDto updateStockExchange(Long id, StockExchangeVo stockExchangeVo);
    Optional<StockExchangeDto> findStockExchangeById(Long id);
    PageDto<StockExchangeDto> findAll(String keyword, Pageable pageable) ;
    StockExchangeDto convertToDto(StockExchangeEntity stockExchangeEntity);
    Optional<StockExchangeEntity> findById(Long id);
    PageDto<StockExchangeDto> getExcludeStockExchangeByCompanyId(Long id, Pageable pageable);
}