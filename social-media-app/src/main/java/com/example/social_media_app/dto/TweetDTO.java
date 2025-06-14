package com.example.social_media_app.dto;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;

@Data
public class TweetDTO {
    @NotBlank
    private String content;
}
