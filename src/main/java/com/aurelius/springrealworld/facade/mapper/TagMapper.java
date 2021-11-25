package com.aurelius.springrealworld.facade.mapper;

import com.aurelius.springrealworld.repository.entities.TagEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TagMapper {
   public List<String> toList(List<TagEntity> tagEntityList) {
      return tagEntityList
              .stream()
              .map(TagEntity::getName)
              .collect(Collectors.toList());
   }
}
