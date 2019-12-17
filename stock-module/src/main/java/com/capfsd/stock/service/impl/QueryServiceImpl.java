package com.capfsd.stock.service.impl;

import com.capfsd.stock.dto.*;
import com.capfsd.stock.entity.IpoDetailEntity;
import com.capfsd.stock.repository.CompanyRepository;
import com.capfsd.stock.repository.IpoDetailRepository;
import com.capfsd.stock.service.IPageService;
import com.capfsd.stock.service.IQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QueryServiceImpl implements IQueryService {
    @Autowired
    private IPageService pageService;

    @Autowired
    private IpoDetailRepository ipoDetailRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Override
    public List<CompositeCompanyDto> findByStockExchangeCode(String stockExchangeCode) {
        List<CompositeCompanyDto> compositeCompanyDtoList = new ArrayList<CompositeCompanyDto>();
        List<IpoDetailEntity> ipoDetailEntityList = ipoDetailRepository.findByStockExchangeCode(stockExchangeCode);
        for (int i = 0; i < ipoDetailEntityList.size(); i++) {
            IpoDetailEntity ipoDetailEntity = ipoDetailEntityList.get(i);
            CompositeCompanyDto compositeCompanyDto = new CompositeCompanyDto();
            compositeCompanyDto.setCompanyName(ipoDetailEntity.getCompanyEntity().getCompanyName());
            compositeCompanyDto.setCompanyCode(ipoDetailEntity.getCompanyCode());
            compositeCompanyDto.setStockExchangeCode(stockExchangeCode);
            compositeCompanyDtoList.add(compositeCompanyDto);
        }
        return compositeCompanyDtoList;
    }

    public List<CompositeCompanyDto> findByCompanyCodeAndStockExchangeCode(String stockExchangeCode, String companyCode) {
        List<CompositeCompanyDto> compositeCompanyDtoList = new ArrayList<CompositeCompanyDto>();
        List<IpoDetailEntity> ipoDetailEntityList = ipoDetailRepository.findByStockExchangeCode(stockExchangeCode);
        for (int i = 0; i < ipoDetailEntityList.size(); i++) {
            IpoDetailEntity ipoDetailEntity = ipoDetailEntityList.get(i);
            CompositeCompanyDto compositeCompanyDto = new CompositeCompanyDto();
            if (companyCode.equals(ipoDetailEntity.getCompanyCode())) {
                compositeCompanyDto.setCompanyName(ipoDetailEntity.getCompanyEntity().getCompanyName());
                compositeCompanyDto.setCompanyCode(companyCode);
                compositeCompanyDto.setStockExchangeCode(stockExchangeCode);
                compositeCompanyDtoList.add(compositeCompanyDto);
            }
        }
        return compositeCompanyDtoList;
    }

}