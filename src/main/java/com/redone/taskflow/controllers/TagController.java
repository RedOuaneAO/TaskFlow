package com.redone.taskflow.controllers;

import com.redone.taskflow.dto.tagDto.TagRequestDto;
import com.redone.taskflow.services.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
public class TagController {
    private final TagService tagService;
    @PostMapping("tag")
    public ResponseEntity<Map<String,Object>> addTad(@RequestBody TagRequestDto tagRequestDto){
        return tagService.addTag(tagRequestDto);
    }
}
