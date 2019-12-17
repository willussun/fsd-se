package com.capfsd.stock.service;

import com.capfsd.stock.entity.StockPriceDetailEntity;
import com.capfsd.stock.vo.StockPriceDetailVo;

import java.util.List;
import java.util.Optional;

public interface IStockPriceDetailService {
    void addStockPriceDetail(StockPriceDetailVo stockPriceDetailDto);
    Optional<StockPriceDetailEntity> findById(Long id);
    List<StockPriceDetailEntity> findStockPriceDetailInfo(StockPriceDetailVo stockPriceDetailVo);
    Integer updateStockPriceDetail(StockPriceDetailVo stockPriceDetailVo);
    }
