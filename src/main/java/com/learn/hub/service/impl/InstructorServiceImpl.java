package com.learn.hub.service.impl;

import com.learn.hub.entity.UserEntity;
import com.learn.hub.exception.LearnHubException;
import com.learn.hub.handler.ErrorCode;
import com.learn.hub.mapper.InstructorMapper;
import com.learn.hub.payload.PageResponse;
import com.learn.hub.repo.UserRepository;
import com.learn.hub.service.CourseService;
import com.learn.hub.service.InstructorService;
import com.learn.hub.specification.InstructorSpecs;
import com.learn.hub.vo.FilterInstructorRequest;
import com.learn.hub.vo.Instructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class InstructorServiceImpl implements InstructorService {

    private final UserRepository userRepo;
    private final InstructorMapper instructorMapper;
    private final CourseService courseService;

    @Override
    public PageResponse<Instructor> filterInstructors(FilterInstructorRequest request) {
        Specification<UserEntity> instructorSpec = InstructorSpecs.createInstructorSpec(request);
        PageRequest pageRequest = PageRequest.of(request.getIndex(), request.getSize(), Sort.by(request.getSort(), request.getSortBy()));
        Page<UserEntity> all = userRepo.findAll(instructorSpec, pageRequest);
        return instructorMapper.toInstructor(all);
    }

    @Override
    public Instructor getById(Long id) {
        Optional<UserEntity> userEntity = userRepo.findById(id);
        if (!userEntity.isPresent()) {
            throw new LearnHubException(ErrorCode.INSTRUCTOR_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
        UserEntity user = userEntity.get();
        Instructor instructor = instructorMapper.toInstructor(user);
        instructor.setCourses(courseService.findByInstructor(user));
        return instructor;
    }
}
