package com.wjh.service;

import com.wjh.dto.PageDTO;
import com.wjh.dto.TagDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TagService {

    TagDTO saveTag(TagDTO tagDTO);

    TagDTO getTag(Long id);

    TagDTO getTagByName(String name);

    PageDTO<TagDTO> listTag(Pageable pageable);

    List<TagDTO> listTag();

    List<TagDTO> listTagTop(Integer size);

    List<TagDTO> listTag(String ids);

    TagDTO updateTag(Long id, TagDTO tagDTO);

    void deleteTag(Long id);
}
