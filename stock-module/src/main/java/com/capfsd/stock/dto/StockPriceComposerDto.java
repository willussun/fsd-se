package com.capfsd.stock.dto;

import java.util.ArrayList;
import java.util.List;

public class StockPriceComposerDto {
    public StockPriceHeaderDto getStockPriceHeaderDto() {
        return stockPriceHeaderDto;
    }

    @Override
    public String toString() {
        return "StockPriceComposerDto{" +
                "stockPriceHeaderDto=" + stockPriceHeaderDto +
                ", stockPriceRecordListDto=" + stockPriceRecordListDto +
                '}';
    }

    public void setStockPriceHeaderDto(StockPriceHeaderDto stockPriceHeaderDto) {
        this.stockPriceHeaderDto = stockPriceHeaderDto;
    }

    public List<StockPriceRecordListDto> getStockPriceRecordListDto() {
        return stockPriceRecordListDto;
    }

    public void setStockPriceRecordListDto(List<StockPriceRecordListDto> stockPriceRecordListDto) {
        this.stockPriceRecordListDto = stockPriceRecordListDto;
    }

    private StockPriceHeaderDto stockPriceHeaderDto;
    private List<StockPriceRecordListDto> stockPriceRecordListDto = new ArrayList<StockPriceRecordListDto>() ;
}
