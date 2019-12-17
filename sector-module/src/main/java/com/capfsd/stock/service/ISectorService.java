package com.capfsd.stock.service;

import com.capfsd.stock.dto.PageDto;
import com.capfsd.stock.dto.SectorDto;
import com.capfsd.stock.entity.SectorEntity;
import com.capfsd.stock.vo.SectorVo;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ISectorService {
    void addSector(SectorVo sectorVo);

    Optional<SectorEntity> findById(Long id);

    SectorDto convertToDto(SectorEntity sectorEntity);

    public SectorDto findBySectorCode(String sectorCode);

    public PageDto<SectorDto> findAll(String keyword, Pageable pageable);

}