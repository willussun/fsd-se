package com.capfsd.stock.service.impl;

import com.capfsd.stock.dto.CompanyDto;
import com.capfsd.stock.dto.IpoDetailDto;
import com.capfsd.stock.dto.PageDto;
import com.capfsd.stock.dto.StockExchangeDto;
import com.capfsd.stock.entity.CompanyEntity;
import com.capfsd.stock.entity.IpoDetailEntity;
import com.capfsd.stock.entity.StockExchangeEntity;
import com.capfsd.stock.repository.CompanyRepository;
import com.capfsd.stock.repository.StockExchangeRepository;
import com.capfsd.stock.service.ICompanyService;
import com.capfsd.stock.service.IIpoDetailService;
import com.capfsd.stock.service.IPageService;
import com.capfsd.stock.service.IStockExchangeService;
import com.capfsd.stock.vo.CompanyVo;
import com.capfsd.stock.vo.IpoDetailVo;
import com.capfsd.stock.vo.StockExchangeVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class StockExchangeServiceImpl implements IStockExchangeService {
    @Autowired
    private IPageService pageService;

    @Autowired
    private StockExchangeRepository stockExchangeRepository;

    @Override
    public void addStockExchange(StockExchangeVo stockExchangeVo) {
        StockExchangeEntity stockExchangeEntity = convertToEntity(null,stockExchangeVo);
        stockExchangeRepository.save(stockExchangeEntity);
    }

    @Override
    public void deleteStockExchangeById(Long id) {
        Optional<StockExchangeEntity> optionalStockExchangeEntity = findById(id);
        optionalStockExchangeEntity.ifPresent(stockExchangeEntity -> stockExchangeRepository.delete(stockExchangeEntity));
    }

    @Override
    public StockExchangeDto updateStockExchange(Long id, StockExchangeVo stockExchangeVo) {
        StockExchangeEntity stockExchangeEntity = convertToEntity(id, stockExchangeVo);
        return convertToDto(stockExchangeRepository.save(stockExchangeEntity));
    }

    @Override
    public Optional<StockExchangeDto> findStockExchangeById(Long id) {
        StockExchangeDto stockExchangeDto = null;

        Optional<StockExchangeEntity> optionalStockExchangeEntity = findById(id);
        if (optionalStockExchangeEntity.isPresent()) {
            stockExchangeDto= convertToDto(optionalStockExchangeEntity.get());
        }

        return Optional.ofNullable(stockExchangeDto);
    }

    @Override
    public PageDto<StockExchangeDto> findAll(String keyword, Pageable pageable) {
        Page<StockExchangeEntity> stockExchangePage = stockExchangeRepository.findAllStockExchangesByKeywordWithPagination(keyword, pageable);
        return pageService.convertToPageDto(stockExchangePage, this::convertToDto);
    }

    private StockExchangeEntity convertToEntity(Long id, StockExchangeVo stockExchangeVo) {
        StockExchangeEntity stockExchangeEntity = new StockExchangeEntity();
        BeanUtils.copyProperties(stockExchangeVo, stockExchangeEntity);

        if (id != null) {
            Optional<StockExchangeEntity> optionalStockExchangeEntity = findById(id);
            optionalStockExchangeEntity.ifPresent(r -> {
                stockExchangeEntity.setId(r.getId());
                stockExchangeEntity.setCreatedBy(r.getCreatedBy());
                stockExchangeEntity.setCreatedTime(r.getCreatedTime());
                stockExchangeEntity.setUpdatedBy(r.getUpdatedBy());
                stockExchangeEntity.setUpdatedTime(new Date());
            });
        }

        return stockExchangeEntity;
    }

    @Override
    public StockExchangeDto convertToDto(StockExchangeEntity stockExchangeEntity) {
        StockExchangeDto stockExchangeDto = new StockExchangeDto();
        BeanUtils.copyProperties(stockExchangeEntity, stockExchangeDto);
        return stockExchangeDto;
    }

    @Override
    public Optional<StockExchangeEntity> findById(Long id) {
        return stockExchangeRepository.findById(id);
    }

    @Override
    public PageDto<StockExchangeDto> getExcludeStockExchangeByCompanyId(Long id, Pageable pageable) {
        Page<StockExchangeEntity> stockExchangePage = stockExchangeRepository.getExcludeStockExchangeByCompanyId(id, pageable);
        return pageService.convertToPageDto(stockExchangePage, this::convertToDto);
    }
}