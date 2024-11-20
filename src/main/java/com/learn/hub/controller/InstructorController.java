package com.learn.hub.controller;


import com.learn.hub.payload.AppResponse;
import com.learn.hub.payload.PageResponse;
import com.learn.hub.service.InstructorService;
import com.learn.hub.vo.FilterInstructorRequest;
import com.learn.hub.vo.Instructor;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/instructors")
@RequiredArgsConstructor
public class InstructorController {

    private final InstructorService instructorService;

    @PostMapping("/filter")
    public AppResponse<PageResponse<Instructor>> filterInstructors(@Valid @RequestBody FilterInstructorRequest request) {
        return new AppResponse<>(instructorService.filterInstructors(request));
    }

    @GetMapping("/{id}")
    public AppResponse<Instructor> getById(@PathVariable Long id) {
        return new AppResponse<>(instructorService.getById(id));
    }

}
