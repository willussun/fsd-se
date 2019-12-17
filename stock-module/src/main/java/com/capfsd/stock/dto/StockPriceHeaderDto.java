package com.capfsd.stock.dto;

import java.util.ArrayList;
import java.util.List;

public class StockPriceHeaderDto {
    @Override
    public String toString() {
        return "StockPriceHeaderDto{" +
                "stockPriceHeaderRecordDtoList=" + stockPriceHeaderRecordDtoList +
                '}';
    }

    public List<StockPriceHeaderRecordDto> getStockPriceHeaderRecordDtoList() {
        return stockPriceHeaderRecordDtoList;
    }

    public void setStockPriceHeaderRecordDtoList(List<StockPriceHeaderRecordDto> stockPriceHeaderRecordDtoList) {
        this.stockPriceHeaderRecordDtoList = stockPriceHeaderRecordDtoList;
    }

    private List<StockPriceHeaderRecordDto> stockPriceHeaderRecordDtoList = new ArrayList<StockPriceHeaderRecordDto>();
}
