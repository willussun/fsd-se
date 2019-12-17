package com.capfsd.stock.controller;

import com.capfsd.stock.dto.PageDto;
import com.capfsd.stock.dto.StockExchangeDto;
import com.capfsd.stock.service.IStockExchangeService;
import com.capfsd.stock.util.LogUtils;
import com.capfsd.stock.util.ResponseResult;
import com.capfsd.stock.vo.StockExchangeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping(value = "/stockexchanges")
public class StockExchangeController {

    @Autowired
    private IStockExchangeService stockExchangeService;

    @GetMapping(value = "/exclude/{id}")
    public ResponseResult<PageDto<StockExchangeDto>> getExcludeStockExchangeByCompanyId(
            @PageableDefault(value = 1000 ) Pageable pageable,
            @PathVariable(value = "id") Long id) {
        PageDto<StockExchangeDto> stockExchangePage = stockExchangeService.getExcludeStockExchangeByCompanyId(id, pageable);
        return ResponseResult.success("get excluded stock exchange successfully", stockExchangePage, null);
    }


    @GetMapping()
    public ResponseResult<PageDto<StockExchangeDto>> getAll(
            @RequestParam(value = "q", defaultValue = "", required = false) String keyword,
            @PageableDefault(value = 1000 ) Pageable pageable) {
        PageDto<StockExchangeDto> stockExchangePage = stockExchangeService.findAll(keyword, pageable);
        return ResponseResult.success("get stock exchanges successfully", stockExchangePage, null);
    }

    @PostMapping()
    public ResponseResult create(@Valid @RequestBody StockExchangeVo stockExchangeVo) {
        stockExchangeService.addStockExchange(stockExchangeVo);
        return ResponseResult.success("create stock exchange successfully", null, null);
    }

    @GetMapping(value = "/{id}")
    public ResponseResult<StockExchangeDto> findById(@PathVariable(value = "id") Long id) {
        Optional<StockExchangeDto> optional = stockExchangeService.findStockExchangeById(id);
        if (optional.isPresent()) {
            StockExchangeDto stockExchangeDto = optional.get();
            return ResponseResult.success("get stock exchange successfully", optional.get(), null);
        }
        return null;
    }

    @DeleteMapping(value = "/{id}")
    public ResponseResult deleteById(@PathVariable(value = "id") Long id) {
        stockExchangeService.deleteStockExchangeById(id);
        return ResponseResult.success("delete stock exchange successfully", null, null);
    }

    @PutMapping(value = "/{id}")
    public ResponseResult<StockExchangeDto> updateById(@PathVariable(value = "id") Long id,
                                                       @Valid @RequestBody StockExchangeVo stockExchangeVo) {
        StockExchangeDto stockExchangeDto = stockExchangeService.updateStockExchange(id, stockExchangeVo);
        return ResponseResult.success("update stock exchange successfully", stockExchangeDto, null);
    }

}
