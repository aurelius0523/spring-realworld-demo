package com.aurelius.springrealworld.facade;

import com.aurelius.springrealworld.facade.mapper.TagMapper;
import com.aurelius.springrealworld.repository.TagRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TagFacade {
    private final TagRepository tagRepository;

    private final TagMapper tagMapper;

    public TagFacade(TagRepository tagRepository, TagMapper tagMapper) {
        this.tagRepository = tagRepository;
        this.tagMapper = tagMapper;
    }

    public List<String> getTagList() {
        return tagMapper.toList(tagRepository.findAll());
    }
}
