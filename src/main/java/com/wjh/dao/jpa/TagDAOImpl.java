package com.wjh.dao.jpa;

import com.wjh.dao.TagDAO;
import com.wjh.dto.PageDTO;
import com.wjh.dto.TagDTO;
import com.wjh.exception.NotFoundException;
import com.wjh.model.jpa.Tag;
import com.wjh.repository.jpa.TagRepository;
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
public class TagDAOImpl implements TagDAO {

    @Autowired
    private TagRepository tagRepository;

    @Override
    public TagDTO saveTag(TagDTO tagDTO) {
        Tag tag = new Tag();
        BeanUtils.copyProperties(tagDTO, tag);
        tagRepository.save(tag);
        return tagDTO;
    }

    @Override
    public TagDTO findTagById(Long id) {
        Tag tag = tagRepository.findById(id).get();
        TagDTO tagDTO = new TagDTO();
        BeanUtils.copyProperties(tag, tagDTO);
        return tagDTO;
    }

    @Override
    public TagDTO findTagByName(String name) {
        Tag tag = tagRepository.findByName(name);
        TagDTO tagDTO = new TagDTO();
        BeanUtils.copyProperties(tag, tagDTO);
        return tagDTO;
    }

    @Override
    public PageDTO<TagDTO> listTag(Pageable pageable) {
        Page<Tag> tagPage = tagRepository.findAll(pageable);
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
        List<Tag> tagList = tagRepository.findAll();
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
        Sort sort = Sort.by(Sort.Direction.DESC,"blogs.size");
        Pageable pageable = PageRequest.of(0, size, sort);
        List<Tag> tagList = tagRepository.findTop(pageable);
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
        List<Tag> tagList = tagRepository.findAllById(idList);
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
        Optional<Tag> result = tagRepository.findById(id);
        Tag t = result.get();
        if (t == null) {
            throw new NotFoundException("Couldn't find expected Tag");
        }
        BeanUtils.copyProperties(tagDTO, t);
        tagRepository.save(t);
        TagDTO returnTag = new TagDTO();
        BeanUtils.copyProperties(t, returnTag);

        return returnTag;
    }

    @Override
    public void deleteById(Long id) {
        tagRepository.deleteById(id);
    }
}
