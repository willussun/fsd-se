package com.capfsd.stock.feign;

import com.capfsd.stock.dto.SectorDto;
import com.capfsd.stock.util.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "sector-module")
public interface SectorServiceFeignClient {
    @GetMapping(value = "/sector/code")
    ResponseResult<SectorDto> getBySectorCode(
            @RequestParam(value = "sectorCode", defaultValue = "", required = false) String sectorCode);
}
