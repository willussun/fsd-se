package com.capfsd.stock.service;

import com.capfsd.stock.dto.PageDto;
import org.springframework.data.domain.Page;

public interface IPageService {
    <S, T> PageDto<T> convertToPageDto(Page<S> page, Converter<S, T> converter) ;
}
