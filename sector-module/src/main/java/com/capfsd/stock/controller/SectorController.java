package com.capfsd.stock.controller;

import com.capfsd.stock.dto.PageDto;
import com.capfsd.stock.dto.SectorDto;
import com.capfsd.stock.service.ISectorService;
import com.capfsd.stock.util.ResponseResult;
import com.capfsd.stock.vo.SectorVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/sector")
public class SectorController {

    @Autowired
    private ISectorService sectorService;

    @PostMapping()
    public ResponseResult create(@Valid @RequestBody SectorVo sectorVo) {
        sectorService.addSector(sectorVo);
        return ResponseResult.success("create sector successfully", null, null);
    }

    @GetMapping(value = "/all")
    public ResponseResult<PageDto<SectorDto>> getAll (
            @RequestParam(value = "keyword", defaultValue = "", required = false) String keyword,
            @PageableDefault(value = 1000, sort = {"updatedTime"}) Pageable pageable) {
        PageDto<SectorDto> sectorPage = sectorService.findAll(keyword, pageable);
        return ResponseResult.success("get companies successfully", sectorPage, null);
    }

    @GetMapping(value = "/code")
    public ResponseResult<SectorDto> getBySectorCode(
            @RequestParam(value = "sectorCode", defaultValue = "", required = false) String sectorCode)
    {
        SectorDto sectorDto = sectorService.findBySectorCode(sectorCode);
        return ResponseResult.success("get sector successfully", sectorDto, null);
    }
}
