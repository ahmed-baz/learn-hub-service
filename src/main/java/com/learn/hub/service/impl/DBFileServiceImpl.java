package com.learn.hub.service.impl;

import com.learn.hub.entity.CourseEntity;
import com.learn.hub.entity.CourseImageEntity;
import com.learn.hub.enums.FileStorageModeEnum;
import com.learn.hub.exception.LearnHubException;
import com.learn.hub.handler.ErrorCode;
import com.learn.hub.repo.CourseImageRepository;
import com.learn.hub.repo.CourseRepository;
import com.learn.hub.service.FileService;
import com.learn.hub.utils.ImageUtil;
import com.learn.hub.vo.ImageResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.UUID;

@Log4j2
@Service
@RequiredArgsConstructor
@ConditionalOnProperty(value = "file.upload.db", havingValue = "true", matchIfMissing = true)
public class DBFileServiceImpl implements FileService {

    private final CourseRepository courseRepo;
    private final CourseImageRepository courseImageRepo;

    @Override
    @Transactional
    public void uploadCourseCoverImage(MultipartFile file, Long courseId) {
        Optional<CourseEntity> optionalCourseEntity = courseRepo.findById(courseId);
        if (!optionalCourseEntity.isPresent() || !getUserName().equals(optionalCourseEntity.get().getCreatedBy())) {
            throw new LearnHubException(ErrorCode.COURSE_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
        try {
            CourseEntity course = optionalCourseEntity.get();
            CourseImageEntity courseImage = course.getCourseImage();
            if (null != courseImage) {
                courseImageRepo.delete(courseImage);
            }
            CourseImageEntity newCourseImage = CourseImageEntity.builder()
                    .code(UUID.randomUUID().toString())
                    .name(file.getOriginalFilename())
                    .type(file.getContentType())
                    .data(ImageUtil.compressImage(file.getBytes()))
                    .mode(FileStorageModeEnum.DB)
                    .build();
            course.setCourseImage(newCourseImage);
            courseRepo.save(course);
        } catch (Exception ex) {
            log.error("failed to upload course cover image", ex);
            throw new LearnHubException(ErrorCode.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ImageResponse getImageById(Long imageId) {
        CourseImageEntity courseImage = courseImageRepo.findById(imageId).orElseThrow(() -> new LearnHubException(ErrorCode.IMAGE_NOT_FOUND, HttpStatus.NOT_FOUND));
        return ImageResponse.builder()
                .id(imageId)
                .data(ImageUtil.decompressImage(courseImage.getData()))
                .name(courseImage.getName())
                .code(courseImage.getCode())
                .type(courseImage.getType())
                .build();
    }

    @Override
    public ImageResponse getImageByCourseId(Long courseId) {
        CourseEntity course = courseRepo.findById(courseId).orElseThrow(() -> new LearnHubException(ErrorCode.COURSE_NOT_FOUND, HttpStatus.NOT_FOUND));
        CourseImageEntity coverImage = course.getCourseImage();
        if (null != coverImage) {
            return getImageById(coverImage.getId());
        }
        throw new LearnHubException(ErrorCode.IMAGE_NOT_FOUND, HttpStatus.NOT_FOUND);
    }

    private String getUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

}
