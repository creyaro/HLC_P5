package com.hlc.studentsservice;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient("subjects-service")
public interface SubjectsClient {

    @GetMapping("/subjects")
    List<String> getAllSubjects();
}
