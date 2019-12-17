package com.capfsd.stock.service;

import com.capfsd.stock.dto.StockPriceComposerDto;
import com.capfsd.stock.dto.StockPriceDetailDto;
import com.capfsd.stock.entity.StockPriceDetailEntity;

import java.util.HashMap;
import java.util.List;

public interface IStockPriceReportService {
    StockPriceComposerDto composeList(List<String> companyCode, List<String> sectorCode, String stockExchangeCode, String startDate, String endDate, String duration) throws Exception;
    HashMap<String, Object> compareMultiJSONResult(List<String> companyCode, List<String> sectorCode, String stockExchangeCode, String startDate, String endDate, String duration) throws Exception;

    HashMap<String, Object> compareSingleJSONResult(List<String> companyCode, List<String> sectorCode, String stockExchangeCode, String startDate, String endDate, String duration) throws Exception;
    List<StockPriceDetailDto>exportResult(List<String> companyCode, List<String> sectorCode, String stockExchangeCode, String startDate, String
            endDate, String duration) throws Exception;

    List<StockPriceDetailEntity>getStockPriceDetail(String companyCode, String stockExchangeCode);
}
