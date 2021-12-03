package com.wjh.service.impl;

import com.wjh.dao.TypeDAO;
import com.wjh.dto.PageDTO;
import com.wjh.dto.TypeDTO;
import com.wjh.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class TypeServiceImpl implements TypeService {

    @Autowired
    private TypeDAO typeDAO;

    @Transactional
    @Override
    public TypeDTO saveType(TypeDTO type) {
        return typeDAO.saveType(type);
    }

    @Transactional
    @Override
    public TypeDTO getType(Long id) {
        return typeDAO.findTypeById(id);
    }

    @Transactional
    @Override
    public TypeDTO getTypeByName(String name) {
        return typeDAO.findTypeByName(name);
    }

    @Transactional
    @Override
    public PageDTO<TypeDTO> listType(Pageable pageable) {
        return typeDAO.listType(pageable);
    }

    @Override
    public List<TypeDTO> listType() {
        return typeDAO.listType();
    }

    @Override
    public List<TypeDTO> listTypeTop(Integer size) {
        return typeDAO.listTypeTop(size);
    }

    @Transactional
    @Override
    public TypeDTO updateType(Long id, TypeDTO type) {
        return typeDAO.updateTypeById(id, type);
    }

    @Transactional
    @Override
    public void deleteType(Long id) {
        typeDAO.deleteById(id);
    }
}
