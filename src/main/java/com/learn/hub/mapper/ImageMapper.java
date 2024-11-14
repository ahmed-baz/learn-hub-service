package com.learn.hub.mapper;

import com.learn.hub.entity.CourseImageEntity;
import com.learn.hub.enums.FileStorageModeEnum;
import com.learn.hub.service.FileService;
import com.learn.hub.utils.ImageUtil;
import com.learn.hub.vo.ImageResponse;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Mapper
@Component
public class ImageMapper {

    @Autowired
    private FileService fileService;

    ImageResponse toImageResponse(CourseImageEntity imageEntity) {
        if (imageEntity == null) return null;
        if (FileStorageModeEnum.SERVER.equals(imageEntity.getMode()) && imageEntity.getPath() != null) {
            return fileService.getImageById(imageEntity.getId());
        } else if (FileStorageModeEnum.DB.equals(imageEntity.getMode())
                   && imageEntity.getData() != null
                   && imageEntity.getData().length > 0
        ) {
            return ImageResponse.builder()
                    .id(imageEntity.getId())
                    .data(ImageUtil.decompressImage(imageEntity.getData()))
                    .name(imageEntity.getName())
                    .code(imageEntity.getCode())
                    .type(imageEntity.getType())
                    .build();
        }
        return null;
    }

}
