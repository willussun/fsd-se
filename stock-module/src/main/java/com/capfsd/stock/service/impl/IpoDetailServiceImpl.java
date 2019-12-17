package com.capfsd.stock.service.impl;
import com.capfsd.stock.dto.CompanyDto;
import com.capfsd.stock.dto.IpoDetailDto;
import com.capfsd.stock.dto.PageDto;
import com.capfsd.stock.dto.StockExchangeDto;
import com.capfsd.stock.entity.CompanyEntity;
import com.capfsd.stock.entity.StockExchangeEntity;
import com.capfsd.stock.repository.IpoDetailRepository;
import com.capfsd.stock.service.ICompanyService;
import com.capfsd.stock.service.IIpoDetailService;
import com.capfsd.stock.service.IPageService;
import com.capfsd.stock.service.IStockExchangeService;
import com.capfsd.stock.util.LogUtils;
import com.capfsd.stock.vo.IpoDetailVo;
import com.capfsd.stock.entity.IpoDetailEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class IpoDetailServiceImpl implements IIpoDetailService {

    @Autowired
    private IPageService pageService;

    @Autowired
    private ICompanyService companyService;

    @Autowired
    private IStockExchangeService stockExchangeService;

    @Autowired
    private IpoDetailRepository ipoDetailRepository;

    @Override
    public void addIpoDetail(IpoDetailVo ipoDetailVo) throws ParseException {
        IpoDetailEntity ipoDetailEntity = convertToEntity(null, ipoDetailVo);
        java.sql.Date newDateSql = java.sql.Date.valueOf(ipoDetailVo.getOpenDatetime());
        java.sql.Time newTimeSql = java.sql.Time.valueOf("00:00:00");
        java.util.Date openDateTime = new java.util.Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        ipoDetailEntity.setOpenDatetime(f.parse(newDateSql.toString() + " " + newTimeSql.toString()));

        if (ipoDetailVo.getCompanyId() != null) {
            Optional<CompanyEntity> optionalCompanyEntity = companyService.findById(ipoDetailVo.getCompanyId());
            CompanyEntity companyEntity = null;
            if (optionalCompanyEntity.isPresent()) {
                companyEntity = optionalCompanyEntity.get();
            }
            ipoDetailEntity.setCompanyEntity(companyEntity);
        }
        if (ipoDetailVo.getStockExchangeId() != null) {
            Optional<StockExchangeEntity> optionalStockExchangeEntity = stockExchangeService.findById(ipoDetailVo.getStockExchangeId());
            StockExchangeEntity stockExchangeEntity = null;
            if (optionalStockExchangeEntity.isPresent()) {
                stockExchangeEntity = optionalStockExchangeEntity.get();
            }
            ipoDetailEntity.setStockExchangeEntity(stockExchangeEntity);
        }
        ipoDetailRepository.save(ipoDetailEntity);
    }

    @Override
    public void deleteIpoDetailById(Long id) {
        Optional<IpoDetailEntity> optionalIPODetailEntity = findById(id);
        optionalIPODetailEntity.ifPresent(ipoDetailEntity -> ipoDetailRepository.delete(ipoDetailEntity));
    }

    @Override
    public IpoDetailDto updateIpoDetail(Long id, IpoDetailVo ipoDetailVo) throws ParseException {
        IpoDetailEntity ipoDetailEntity = convertToEntity(id, ipoDetailVo);
        java.sql.Date newDateSql = java.sql.Date.valueOf(ipoDetailVo.getOpenDatetime());
        java.sql.Time newTimeSql = java.sql.Time.valueOf("00:00:00");
        java.util.Date openDateTime = new java.util.Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        ipoDetailEntity.setOpenDatetime(f.parse(newDateSql.toString() + " " + newTimeSql.toString()));

        if (ipoDetailVo.getCompanyId() != null) {
            Optional<CompanyEntity> optionalCompanyEntity = companyService.findById(ipoDetailVo.getCompanyId());
            CompanyEntity companyEntity = null;
            if (optionalCompanyEntity.isPresent()) {
                companyEntity = optionalCompanyEntity.get();
            }
            ipoDetailEntity.setCompanyEntity(companyEntity);
        }
        if (ipoDetailVo.getStockExchangeId() != null) {
            Optional<StockExchangeEntity> optionalStockExchangeEntity = stockExchangeService.findById(ipoDetailVo.getStockExchangeId());
            StockExchangeEntity stockExchangeEntity = null;
            if (optionalStockExchangeEntity.isPresent()) {
                stockExchangeEntity = optionalStockExchangeEntity.get();
            }
            ipoDetailEntity.setStockExchangeEntity(stockExchangeEntity);
        }

        return convertToDto(ipoDetailRepository.save(ipoDetailEntity));

    }

    @Override
    public Optional<IpoDetailDto> findIpoDetailById(Long id) {
        IpoDetailDto ipoDetailDto = null;
        Optional<IpoDetailEntity> optionalIpoDetailEntity = findById(id);
        System.out.println(optionalIpoDetailEntity);
        if (optionalIpoDetailEntity.isPresent()) {
            IpoDetailEntity ipoDetailEntity = optionalIpoDetailEntity.get();
            System.out.println(ipoDetailEntity);
            ipoDetailDto = convertToDto(optionalIpoDetailEntity.get());
            ipoDetailDto.setOpenDatetime(optionalIpoDetailEntity.get().getOpenDatetime().toString());
        }
//        IpoDetailEntity ipoDetailEntity = optionalIpoDetailEntity.get();
//        ipoDetailDto = convertToDto(ipoDetailEntity);
        return Optional.ofNullable(ipoDetailDto);
    }

//    @Override
//    public PageDto<IpoDetailDto> findAll(String keyword, Pageable pageable) {
//        Page<IpoDetailEntity> ipoDetailEntityPage = ipoDetailRepository.findAllByIpoCodeContainsIgnoreCase(keyword, pageable);
//        return pageService.convertToPageDto(ipoDetailEntityPage, this::convertToDto);
//    }

    @Override
    public PageDto<IpoDetailDto> findAll(String keyword, Pageable pageable) {
        Page<IpoDetailEntity> ipoDetailEntityPage = ipoDetailRepository.findAllIpoDetailsByKeywordWithPagination(keyword, pageable);
        return pageService.convertToPageDto(ipoDetailEntityPage, this::convertToDto);
    }

    // If id not null, means need to update or query information, if id is null, means new update
    private IpoDetailEntity convertToEntity(Long id, IpoDetailVo ipoDetailVo) {
        IpoDetailEntity ipoDetailEntity = new IpoDetailEntity();
        BeanUtils.copyProperties(ipoDetailVo, ipoDetailEntity);
        if (id != null) {
            Optional<IpoDetailEntity> optionalIPODetailEntity = findById(id);
            optionalIPODetailEntity.ifPresent(r -> {
                ipoDetailEntity.setId(r.getId());
                ipoDetailEntity.setCreatedBy(r.getCreatedBy());
                ipoDetailEntity.setCreatedTime(r.getCreatedTime());
                ipoDetailEntity.setUpdatedBy(r.getUpdatedBy());
                ipoDetailEntity.setUpdatedTime(new Date());
            });
        }
        return ipoDetailEntity;

    }

    @Override
    public IpoDetailDto convertToDto(IpoDetailEntity ipoDetailEntity) {
        IpoDetailDto ipoDetailDto = new IpoDetailDto();
 //       BeanUtils.copyProperties(ipoDetailEntity, ipoDetailDto);
        if(ipoDetailEntity.getOpenDatetime() == null)
            ipoDetailDto.setOpenDatetime(null);
        else ipoDetailDto.setOpenDatetime(ipoDetailEntity.getOpenDatetime().toString());
        ipoDetailDto.setStockExchangeCode(ipoDetailEntity.getStockExchangeCode());
        ipoDetailDto.setCompanyCode(ipoDetailEntity.getCompanyCode());
        ipoDetailDto.setId(ipoDetailEntity.getId());
        ipoDetailDto.setIpoCode(ipoDetailEntity.getIpoCode());
        ipoDetailDto.setPricePerShare(ipoDetailEntity.getPricePerShare());
        ipoDetailDto.setRemarks(ipoDetailEntity.getRemarks());
        ipoDetailDto.setTotalNumberShare(ipoDetailEntity.getTotalNumberShare());
        if (ipoDetailEntity.getCompanyEntity() != null) {
            CompanyDto companyDto = new CompanyDto();
            BeanUtils.copyProperties(ipoDetailEntity.getCompanyEntity(), companyDto);
            ipoDetailDto.setCompanyDto(companyDto);
        }
        if (ipoDetailEntity.getStockExchangeEntity() != null) {
            StockExchangeDto stockExchangeDto = new StockExchangeDto();
            BeanUtils.copyProperties(ipoDetailEntity.getStockExchangeEntity(), stockExchangeDto);
            ipoDetailDto.setStockExchangeDto(stockExchangeDto);
        }
        return ipoDetailDto;

    }


    @Override
    public Optional<IpoDetailEntity> findById(Long id) {
        return ipoDetailRepository.findById(id);

    }

    @Override
    public List<IpoDetailDto> findByStockExchangeCode(String stockExchangeCode){
        List<IpoDetailEntity> ipoDetailEntityList = ipoDetailRepository.findByStockExchangeCode(stockExchangeCode);
        ArrayList<IpoDetailDto> ipoDetailDtoList = new ArrayList<IpoDetailDto>();
        for (IpoDetailEntity ipoDetailEntity : ipoDetailEntityList) {
            IpoDetailDto ipoDetailDto = new IpoDetailDto();
            BeanUtils.copyProperties(ipoDetailEntity, ipoDetailDto);
            ipoDetailDtoList.add(ipoDetailDto);
        }
        return ipoDetailDtoList;
    }

    @Override
    public List<IpoDetailEntity> findAllIpoDetail() {
        return ipoDetailRepository.findAll();
    }
}