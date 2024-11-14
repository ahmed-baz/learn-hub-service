package com.learn.hub.service.impl;

import com.learn.hub.entity.CourseEntity;
import com.learn.hub.entity.CourseImageEntity;
import com.learn.hub.enums.FileStorageModeEnum;
import com.learn.hub.exception.LearnHubException;
import com.learn.hub.handler.ErrorCode;
import com.learn.hub.repo.CourseImageRepository;
import com.learn.hub.repo.CourseRepository;
import com.learn.hub.security.vo.AppUserDetails;
import com.learn.hub.service.FileService;
import com.learn.hub.vo.ImageResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.UUID;

@Log4j2
@Service
@RequiredArgsConstructor
@ConditionalOnProperty(value = "file.upload.db", havingValue = "false")
public class ServerFileServiceImpl implements FileService {

    private final CourseRepository courseRepo;
    private final CourseImageRepository courseImageRepo;
    @Value("${file.upload.directory}")
    private String uploadDirectory;

    @Override
    @Transactional
    public void uploadCourseCoverImage(MultipartFile file, Long courseId) {
        String oldImagePath = null;
        try {
            String code = UUID.randomUUID().toString();
            String filePath = savingFile(file, code);
            CourseEntity course = getCourse(courseId);
            CourseImageEntity courseImage = course.getCourseImage();
            oldImagePath = courseImage != null ? courseImage.getPath() : null;
            updateCourse(course, file, code, filePath);
        } finally {
            deleteOldImage(oldImagePath);
        }
    }

    private String savingFile(MultipartFile file, String code) {
        try {
            //prepare the path
            String uniqueFileName = code + "_" + file.getOriginalFilename();
            Path uploadPath = Path.of(uploadDirectory);
            Path filePath = uploadPath.resolve(uniqueFileName);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            // copy the file to the path
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            return filePath.toString();
        } catch (IOException ex) {
            log.error("failed to save image ", ex);
            throw new LearnHubException(ErrorCode.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private CourseEntity getCourse(Long courseId) {
        AppUserDetails user = getUser();
        Optional<CourseEntity> optionalCourseEntity = courseRepo.findById(courseId);
        if (!optionalCourseEntity.isPresent() || user.getId() != optionalCourseEntity.get().getInstructor().getId()) {
            throw new LearnHubException(ErrorCode.COURSE_NOT_FOUND, HttpStatus.NOT_FOUND);
        }
        return optionalCourseEntity.get();
    }

    private void updateCourse(CourseEntity course, MultipartFile file, String code, String filePath) {
        CourseImageEntity newCourseImage = CourseImageEntity.builder()
                .code(code)
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .path(filePath)
                .mode(FileStorageModeEnum.SERVER)
                .build();
        course.setCourseImage(newCourseImage);
        courseRepo.save(course);
    }

    private void deleteOldImage(String imageDirectory) {
        try {
            if (imageDirectory != null && !imageDirectory.isEmpty()) {
                Path imagePath = Path.of(imageDirectory);
                if (Files.exists(imagePath)) {
                    Files.delete(imagePath);
                }
            }
        } catch (IOException ex) {
            log.error("failed to delete old image {}", imageDirectory, ex);
        }
    }

    private byte[] getImageData(String imageDirectory) {
        try {
            Path imagePath = Path.of(imageDirectory);
            if (Files.exists(imagePath)) {
                return Files.readAllBytes(imagePath);
            } else {
                throw new LearnHubException(ErrorCode.IMAGE_NOT_FOUND, HttpStatus.NOT_FOUND);
            }
        } catch (IOException ex) {
            throw new LearnHubException(ErrorCode.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ImageResponse getImageById(Long imageId) {
        CourseImageEntity courseImage = courseImageRepo.findById(imageId).orElseThrow(() -> new LearnHubException(ErrorCode.IMAGE_NOT_FOUND, HttpStatus.NOT_FOUND));
        return ImageResponse.builder()
                .id(imageId)
                .data(getImageData(courseImage.getPath()))
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

    private AppUserDetails getUser() {
        return (AppUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

}
