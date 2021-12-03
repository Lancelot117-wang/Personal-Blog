package com.wjh.dao.jpa;

import com.wjh.dao.TypeDAO;
import com.wjh.dto.PageDTO;
import com.wjh.dto.TypeDTO;
import com.wjh.exception.NotFoundException;
import com.wjh.model.jpa.Type;
import com.wjh.repository.jpa.TypeRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ConditionalOnProperty(name = "gcp.datastore.enabled", havingValue = "false")
@Repository
public class TypeDAOImpl implements TypeDAO {

    @Autowired
    private TypeRepository typeRepository;

    @Override
    public TypeDTO saveType(TypeDTO typeDTO) {
        Type type = new Type();
        BeanUtils.copyProperties(typeDTO, type);
        typeRepository.save(type);
        return typeDTO;
    }

    @Override
    public TypeDTO findTypeById(Long id) {
        Type type = typeRepository.findById(id).get();
        TypeDTO typeDTO = new TypeDTO();
        BeanUtils.copyProperties(type, typeDTO);
        return typeDTO;
    }

    @Override
    public TypeDTO findTypeByName(String name) {
        Type type = typeRepository.findByName(name);
        TypeDTO typeDTO = new TypeDTO();
        BeanUtils.copyProperties(type, typeDTO);
        return typeDTO;
    }

    @Override
    public PageDTO<TypeDTO> listType(Pageable pageable) {
        Page<Type> typePage = typeRepository.findAll(pageable);
        List<Type> typeList = typePage.getContent();
        List<TypeDTO> typeDTOList = new ArrayList<>();
        typeList.forEach(type -> {
            TypeDTO typeDTO = new TypeDTO();
            BeanUtils.copyProperties(type, typeDTO);
            typeDTOList.add(typeDTO);
        });
        PageDTO<TypeDTO> pageDTO = new PageDTO<>();
        pageDTO.setContent(typeDTOList);
        pageDTO.setTotalPages(typePage.getTotalPages());
        pageDTO.setNumber(typePage.getNumber());
        pageDTO.setFirst(typePage.isFirst());
        pageDTO.setLast(typePage.isLast());
        return pageDTO;
    }

    @Override
    public List<TypeDTO> listType() {
        List<Type> typeList = typeRepository.findAll();
        List<TypeDTO> typeDTOList = new ArrayList<>();
        typeList.forEach(type -> {
            TypeDTO typeDTO = new TypeDTO();
            BeanUtils.copyProperties(type, typeDTO);
            typeDTOList.add(typeDTO);
        });
        return typeDTOList;
    }

    @Override
    public List<TypeDTO> listTypeTop(Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC,"blogs.size");
        Pageable pageable = PageRequest.of(0, size, sort);
        List<Type> typeList = typeRepository.findTop(pageable);
        List<TypeDTO> typeDTOList = new ArrayList<>();
        typeList.forEach(type -> {
            TypeDTO typeDTO = new TypeDTO();
            BeanUtils.copyProperties(type, typeDTO);
            typeDTOList.add(typeDTO);
        });
        return typeDTOList;
    }

    @Override
    public TypeDTO updateTypeById(Long id, TypeDTO typeDTO) {
        Optional<Type> result = typeRepository.findById(id);
        Type t = result.get();
        if (t == null) {
            throw new NotFoundException("Couldn't find expected Type");
        }
        BeanUtils.copyProperties(typeDTO, t);
        typeRepository.save(t);
        TypeDTO returnType = new TypeDTO();
        BeanUtils.copyProperties(t, returnType);

        return returnType;
    }

    @Override
    public void deleteById(Long id) {
        typeRepository.deleteById(id);
    }
}
