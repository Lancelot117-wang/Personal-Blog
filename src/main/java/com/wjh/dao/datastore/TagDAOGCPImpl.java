package com.wjh.dao.datastore;

import com.wjh.dao.TagDAO;
import com.wjh.dto.PageDTO;
import com.wjh.dto.TagDTO;
import com.wjh.exception.NotFoundException;
import com.wjh.model.datastore.Tag;
import com.wjh.repository.datastore.TagDatastoreRepository;
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
public class TagDAOGCPImpl implements TagDAO {

    @Autowired
    private TagDatastoreRepository tagDatastoreRepository;

    @Override
    public TagDTO saveTag(TagDTO tagDTO) {
        Tag tag = new Tag();
        BeanUtils.copyProperties(tagDTO, tag);
        tagDatastoreRepository.save(tag);
        return tagDTO;
    }

    @Override
    public TagDTO findTagById(Long id) {
        Tag tag = tagDatastoreRepository.findById(id).get();
        TagDTO tagDTO = new TagDTO();
        BeanUtils.copyProperties(tag, tagDTO);
        return tagDTO;
    }

    @Override
    public TagDTO findTagByName(String name) {
        Tag tag = tagDatastoreRepository.findByName(name);
        TagDTO tagDTO = new TagDTO();
        BeanUtils.copyProperties(tag, tagDTO);
        return tagDTO;
    }

    @Override
    public PageDTO<TagDTO> listTag(Pageable pageable) {
        Page<Tag> tagPage = tagDatastoreRepository.findAll(pageable);
        List<Tag> tagList = tagPage.getContent();
        List<TagDTO> tagDTOList = new ArrayList<>();
        tagList.forEach(tag -> {
            TagDTO tagDTO = new TagDTO();
            BeanUtils.copyProperties(tag, tagDTO);
            tagDTOList.add(tagDTO);
        });
        PageDTO<TagDTO> pageDTO = new PageDTO<>();
        pageDTO.setContent(tagDTOList);
        pageDTO.setTotalPages(tagPage.getTotalPages());
        pageDTO.setNumber(tagPage.getNumber());
        pageDTO.setFirst(tagPage.isFirst());
        pageDTO.setLast(tagPage.isLast());
        return pageDTO;
    }

    @Override
    public List<TagDTO> listTag() {
        Iterable<Tag> tagList = tagDatastoreRepository.findAll();
        List<TagDTO> tagDTOList = new ArrayList<>();
        tagList.forEach(tag -> {
            TagDTO tagDTO = new TagDTO();
            BeanUtils.copyProperties(tag, tagDTO);
            tagDTOList.add(tagDTO);
        });
        return tagDTOList;
    }

    @Override
    public List<TagDTO> listTagTop(Integer size) {
        List<Tag> tagList = tagDatastoreRepository.findTop(size);
        List<TagDTO> tagDTOList = new ArrayList<>();
        tagList.forEach(tag -> {
            TagDTO tagDTO = new TagDTO();
            BeanUtils.copyProperties(tag, tagDTO);
            tagDTOList.add(tagDTO);
        });
        return tagDTOList;
    }

    @Override
    public List<TagDTO> findAllById(List<Long> idList) {
        Iterable<Tag> tagList = tagDatastoreRepository.findAllById(idList);
        List<TagDTO> tagDTOList = new ArrayList<>();
        tagList.forEach(tag -> {
            TagDTO tagDTO = new TagDTO();
            BeanUtils.copyProperties(tag, tagDTO);
            tagDTOList.add(tagDTO);
        });
        return tagDTOList;
    }

    @Override
    public TagDTO updateTagById(Long id, TagDTO tagDTO) {
        Optional<Tag> result = tagDatastoreRepository.findById(id);
        Tag t = result.get();
        if (t == null) {
            throw new NotFoundException("Couldn't find expected Tag");
        }
        BeanUtils.copyProperties(tagDTO, t);
        tagDatastoreRepository.save(t);
        TagDTO returnTag = new TagDTO();
        BeanUtils.copyProperties(t, returnTag);

        return returnTag;
    }

    @Override
    public void deleteById(Long id) {
        tagDatastoreRepository.deleteById(id);
    }
}
