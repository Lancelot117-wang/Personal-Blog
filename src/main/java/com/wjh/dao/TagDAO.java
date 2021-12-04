package com.wjh.dao;

import com.wjh.dto.PageDTO;
import com.wjh.dto.TagDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TagDAO {

    TagDTO saveTag(TagDTO tagDTO);

    TagDTO findTagById(Long id);

    TagDTO findTagByName(String name);

    PageDTO<TagDTO> listTag(Pageable pageable);

    List<TagDTO> listTag();

    List<TagDTO> listTagTop(Integer size);

    List<TagDTO> findAllById(List<Long> idList);

    TagDTO updateTagById(Long id, TagDTO tagDTO);

    void deleteById(Long id);
}
