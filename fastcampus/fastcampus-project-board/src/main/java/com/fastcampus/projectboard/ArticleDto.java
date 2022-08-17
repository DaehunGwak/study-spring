package com.fastcampus.projectboard;

import lombok.Builder;

import java.io.Serializable;
import java.time.LocalDateTime;

public record ArticleDto(
        String title,
        String content,
        String hashtag,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime modifiedAt,
        String modifiedBy
) implements Serializable {
}
