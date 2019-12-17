package com.capfsd.stock.service.impl;

import com.capfsd.stock.entity.StockPriceDetailEntity;
import com.capfsd.stock.util.LogUtils;
import com.capfsd.stock.util.ResponseResult;
import com.capfsd.stock.vo.StockPriceDetailVo;
import com.capfsd.stock.repository.StockPriceDetailRepository;
import com.capfsd.stock.service.IPageService;
import com.capfsd.stock.service.IStockPriceDetailService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;

import java.util.List;
import java.util.Optional;

@Service
public class StockPriceDetailServiceImpl implements IStockPriceDetailService {
    @Autowired
    private IPageService pageService;

    @Autowired
    private StockPriceDetailRepository stockPriceDetailRepository;

    @Override
    public void addStockPriceDetail(StockPriceDetailVo stockPriceDetailVo) {
        StockPriceDetailEntity stockPriceDetailEntity = convertToEntity(null, stockPriceDetailVo);
        stockPriceDetailRepository.save(stockPriceDetailEntity);
    }

    private StockPriceDetailEntity convertToEntity(Long id, StockPriceDetailVo stockPriceDetailVo) {
        StockPriceDetailEntity stockPriceDetailEntity = new StockPriceDetailEntity();
        BeanUtils.copyProperties(stockPriceDetailVo, stockPriceDetailEntity);
        if (id != null) {
            Optional<StockPriceDetailEntity> optionalStockExchangeEntity = findById(id);
            optionalStockExchangeEntity.ifPresent(r -> {
                stockPriceDetailEntity.setId(r.getId());
                stockPriceDetailEntity.setCreatedBy(r.getCreatedBy());
                stockPriceDetailEntity.setCreatedTime(r.getCreatedTime());
                stockPriceDetailEntity.setUpdatedBy(r.getUpdatedBy());
                stockPriceDetailEntity.setUpdatedTime(new Date());
            });
        }
        return stockPriceDetailEntity;
    }

    @Override
    public Optional<StockPriceDetailEntity> findById(Long id) {
        return stockPriceDetailRepository.findById(id);
    }

    @Override
    public List<StockPriceDetailEntity> findStockPriceDetailInfo(StockPriceDetailVo stockPriceDetailVo) {
        List<StockPriceDetailEntity> stockPriceDetailEntity =
                stockPriceDetailRepository.findStockPriceDetailInfo(stockPriceDetailVo.getCompanyCode(),
                        stockPriceDetailVo.getStockExchangeCode(), stockPriceDetailVo.getSectorCode(),
                        stockPriceDetailVo.getDate(), stockPriceDetailVo.getTime());
        return stockPriceDetailEntity;
    }

    @Override
    public Integer updateStockPriceDetail(StockPriceDetailVo stockPriceDetailVo) {
                Integer status = stockPriceDetailRepository.updateStockPriceDetailEntity(stockPriceDetailVo.getCompanyCode(),
                        stockPriceDetailVo.getStockExchangeCode(), stockPriceDetailVo.getSectorCode(),
                        stockPriceDetailVo.getDate(), stockPriceDetailVo.getTime(),
                        stockPriceDetailVo.getPricePerShare());
        return status;
    }
}
