package com.capfsd.stock.service.impl;

import com.capfsd.stock.dto.PageDto;
import com.capfsd.stock.dto.SectorDto;
import com.capfsd.stock.entity.SectorEntity;
import com.capfsd.stock.repository.SectorRepository;
import com.capfsd.stock.service.IPageService;
import com.capfsd.stock.service.ISectorService;
import com.capfsd.stock.vo.SectorVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class SectorServiceImpl implements ISectorService {
    @Autowired
    private IPageService pageService;

    @Autowired
    private SectorRepository sectorRepository;

    @Override
    public void addSector(SectorVo sectorVo) {
        SectorEntity sectorEntity = convertToEntity(null,sectorVo);
        sectorRepository.save(sectorEntity);
    }

    private SectorEntity convertToEntity(Long id, SectorVo sectorVo) {
        SectorEntity sectorEntity = new SectorEntity();
        BeanUtils.copyProperties(sectorVo, sectorEntity);

        if (id != null) {
            Optional<SectorEntity> optionalSectorEntity = findById(id);
            optionalSectorEntity.ifPresent(r -> {
                sectorEntity.setId(r.getId());
                sectorEntity.setCreatedBy(r.getCreatedBy());
                sectorEntity.setCreatedTime(r.getCreatedTime());
                sectorEntity.setUpdatedBy(r.getUpdatedBy());
                sectorEntity.setUpdatedTime(new Date());
            });
        }

        return sectorEntity;
    }

    @Override
    public Optional<SectorEntity> findById(Long id){
        return sectorRepository.findById(id);
    }


    @Override
    public SectorDto convertToDto(SectorEntity sectorEntity) {
        SectorDto sectorDto = new SectorDto();
        BeanUtils.copyProperties(sectorEntity, sectorDto);
        return sectorDto;
    }

    @Override
    public SectorDto findBySectorCode(String sectorCode) {
        List<SectorDto> sectorDtoList = new ArrayList<SectorDto>();
        List<SectorEntity> sectorEntityList = sectorRepository.findBySectorCode(sectorCode);
        if(sectorEntityList.size() > 1)
            return null;
            SectorDto sectorDto = new SectorDto();
            sectorDto = convertToDto(sectorEntityList.get(0));
            sectorDtoList.add(sectorDto);
        return sectorDto;
    }

    @Override
    public PageDto<SectorDto> findAll(String keyword, Pageable pageable) {
        Page<SectorEntity> sectorPage = sectorRepository.findAllSectorsByKeywordWithPagination(keyword, pageable);
        return pageService.convertToPageDto(sectorPage, this::convertToDto);
    }

}
