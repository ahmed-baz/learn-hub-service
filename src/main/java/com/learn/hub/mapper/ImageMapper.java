package com.learn.hub.mapper;

import com.learn.hub.entity.CourseImageEntity;
import com.learn.hub.utils.ImageUtil;
import com.learn.hub.vo.ImageResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper
public interface ImageMapper {

    @Mapping(source = "data", target = "data", qualifiedByName = "mapImageData")
    ImageResponse toImageResponse(CourseImageEntity imageEntity);

    @Named("mapImageData")
    default byte[] mapImageData(byte[] data) {
        return ImageUtil.decompressImage(data);
    }

}
