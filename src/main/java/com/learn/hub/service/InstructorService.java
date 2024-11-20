package com.learn.hub.service;

import com.learn.hub.payload.PageResponse;
import com.learn.hub.vo.FilterInstructorRequest;
import com.learn.hub.vo.Instructor;

public interface InstructorService {

    PageResponse<Instructor> filterInstructors(FilterInstructorRequest request);

    Instructor getById(Long id);
}
