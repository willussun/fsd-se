package com.capfsd.stock.service;

import com.capfsd.stock.dto.IpoDetailDto;
import com.capfsd.stock.dto.PageDto;
import com.capfsd.stock.dto.IpoDetailDto;
import com.capfsd.stock.entity.IpoDetailEntity;
import com.capfsd.stock.vo.IpoDetailVo;
import org.springframework.data.domain.Pageable;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

public interface IIpoDetailService {
    void addIpoDetail(IpoDetailVo ipoDetailVo) throws ParseException;
    void deleteIpoDetailById(Long id) ;
    IpoDetailDto updateIpoDetail(Long id, IpoDetailVo ipoDetailVo) throws ParseException;
    Optional<IpoDetailDto> findIpoDetailById(Long id);
    PageDto<IpoDetailDto> findAll(String keyword, Pageable pageable);
    IpoDetailDto convertToDto(IpoDetailEntity ipoDetailEntity);
    Optional<IpoDetailEntity> findById(Long id);
    List<IpoDetailDto> findByStockExchangeCode(String stockExchangeCode);
    List<IpoDetailEntity> findAllIpoDetail();

    }