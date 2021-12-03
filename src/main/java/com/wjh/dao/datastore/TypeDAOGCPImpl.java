package com.wjh.dao.datastore;

import com.wjh.dao.TypeDAO;
import com.wjh.dto.PageDTO;
import com.wjh.dto.TypeDTO;
import com.wjh.exception.NotFoundException;
import com.wjh.model.datastore.Type;
import com.wjh.repository.datastore.TypeDatastoreRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ConditionalOnProperty(name = "gcp.datastore.enabled", havingValue = "true")
@Repository
public class TypeDAOGCPImpl implements TypeDAO {

    @Autowired
    private TypeDatastoreRepository typeDatastoreRepository;

    @Override
    public TypeDTO saveType(TypeDTO typeDTO) {
        Type type = new Type();
        BeanUtils.copyProperties(typeDTO, type);
        typeDatastoreRepository.save(type);
        return typeDTO;
    }

    @Override
    public TypeDTO findTypeById(Long id) {
        Type type = typeDatastoreRepository.findById(id).get();
        TypeDTO typeDTO = new TypeDTO();
        BeanUtils.copyProperties(type, typeDTO);
        return typeDTO;
    }

    @Override
    public TypeDTO findTypeByName(String name) {
        Type type = typeDatastoreRepository.findByName(name);
        TypeDTO typeDTO = new TypeDTO();
        BeanUtils.copyProperties(type, typeDTO);
        return typeDTO;
    }

    @Override
    public PageDTO<TypeDTO> listType(Pageable pageable) {
        Page<Type> typePage = typeDatastoreRepository.findAll(pageable);
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
        Iterable<Type> typeList = typeDatastoreRepository.findAll();
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
        List<Type> typeList = typeDatastoreRepository.findTop(size);
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
        Optional<Type> result = typeDatastoreRepository.findById(id);
        Type t = result.get();
        if (t == null) {
            throw new NotFoundException("Couldn't find expected Type");
        }
        BeanUtils.copyProperties(typeDTO, t);
        typeDatastoreRepository.save(t);
        TypeDTO returnType = new TypeDTO();
        BeanUtils.copyProperties(t, returnType);

        return returnType;
    }

    @Override
    public void deleteById(Long id) {
        typeDatastoreRepository.deleteById(id);
    }
}
