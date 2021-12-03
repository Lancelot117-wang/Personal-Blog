package com.wjh.service;

import com.wjh.dto.PageDTO;
import com.wjh.dto.TypeDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TypeService {

    TypeDTO saveType(TypeDTO type);

    TypeDTO getType(Long id);

    TypeDTO getTypeByName(String name);

    PageDTO<TypeDTO> listType(Pageable pageable);

    List<TypeDTO> listType();

    List<TypeDTO> listTypeTop(Integer size);

    TypeDTO updateType(Long id, TypeDTO type);

    void deleteType(Long id);
}
