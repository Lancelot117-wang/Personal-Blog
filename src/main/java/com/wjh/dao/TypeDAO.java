package com.wjh.dao;

import com.wjh.dto.PageDTO;
import com.wjh.dto.TypeDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TypeDAO {

    TypeDTO saveType(TypeDTO typeDTO);

    TypeDTO findTypeById(Long id);

    TypeDTO findTypeByName(String name);

    PageDTO<TypeDTO> listType(Pageable pageable);

    List<TypeDTO> listType();

    List<TypeDTO> listTypeTop(Integer size);

    TypeDTO updateTypeById(Long id, TypeDTO typeDTO);

    void deleteById(Long id);
}
