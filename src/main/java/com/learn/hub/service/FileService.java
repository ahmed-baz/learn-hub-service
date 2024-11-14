package com.learn.hub.service;

import com.learn.hub.vo.ImageResponse;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    void uploadCourseCoverImage(MultipartFile file, Long courseId);

    ImageResponse getImageById(Long imageId);

    ImageResponse getImageByCourseId(Long courseId);

}
