package com.learn.hub.vo;


import lombok.*;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ImageResponse {

    private Long id;
    private String name;
    private String code;
    private String path;
    private String type;
    private byte[] data;
}
