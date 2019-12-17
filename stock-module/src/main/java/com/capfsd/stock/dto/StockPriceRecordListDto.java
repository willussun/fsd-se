package com.capfsd.stock.dto;

import java.util.ArrayList;
import java.util.List;

public class StockPriceRecordListDto {
    private String companyName;
    private String companyCode;
    @Override
    public String toString() {
        return "StockPriceRecordListDto{" +
                ", companyName='" + companyName + '\'' +
                ", companyCode='" + companyCode + '\'' +
                ", sectorCode='" + sectorCode + '\'' +
                ", stockPriceRecordDto=" + stockPriceRecordDto +
                '}';
    }

    public void setStockPriceRecordDto(List<StockPriceRecordDto> stockPriceRecordDto) {
        this.stockPriceRecordDto = stockPriceRecordDto;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getSectorCode() {
        return sectorCode;
    }

    public void setSectorCode(String sectorCode) {
        this.sectorCode = sectorCode;
    }

    public List<StockPriceRecordDto> getStockPriceRecordDto() {
        return stockPriceRecordDto;
    }

    private String sectorCode;
    private List<StockPriceRecordDto> stockPriceRecordDto = new ArrayList<StockPriceRecordDto>();
}
