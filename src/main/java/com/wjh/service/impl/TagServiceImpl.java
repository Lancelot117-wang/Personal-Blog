package com.wjh.service.impl;

import com.wjh.dao.TagDAO;
import com.wjh.dto.PageDTO;
import com.wjh.dto.TagDTO;
import com.wjh.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagDAO tagDAO;

    @Transactional
    @Override
    public TagDTO saveTag(TagDTO tagDTO) {
        return tagDAO.saveTag(tagDTO);
    }

    @Transactional
    @Override
    public TagDTO getTag(Long id) {
        return tagDAO.findTagById(id);
    }

    @Transactional
    @Override
    public TagDTO getTagByName(String name) {
        return tagDAO.findTagByName(name);
    }

    @Transactional
    @Override
    public PageDTO<TagDTO> listTag(Pageable pageable) {
        return tagDAO.listTag(pageable);
    }

    @Override
    public List<TagDTO> listTag() {
        return tagDAO.listTag();
    }

    @Override
    public List<TagDTO> listTagTop(Integer size) {
        return tagDAO.listTagTop(size);
    }

    @Override
    public List<TagDTO> listTag(String ids) {
        return tagDAO.findAllById(convertToList(ids));
    }

    private List<Long> convertToList(String ids) {
        List<Long> list = new ArrayList<>();
        if(!"".equals(ids) && ids != null){
            String[] idarray = ids.split(",");
            for(int i=0; i< idarray.length; i++){
                list.add(Long.valueOf(idarray[i]));
            }
        }
        return list;
    }

    @Transactional
    @Override
    public TagDTO updateTag(Long id, TagDTO tagDTO) {
        return tagDAO.updateTagById(id, tagDTO);
    }

    @Transactional
    @Override
    public void deleteTag(Long id) {
        tagDAO.deleteById(id);
    }
}
