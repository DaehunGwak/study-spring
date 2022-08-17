package com.fastcampus.projectboard.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@ToString
@Table(indexes = {
        @Index(columnList = "title", name = "idx_article_title"),
        @Index(columnList = "hashtag", name = "idx_article_hashtag"),
        @Index(columnList = "createdAt", name = "idx_article_created_at"),
        @Index(columnList = "createdBy", name = "idx_article_created_by")
})
@Entity
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter @Column(nullable = false) private String title;
    @Setter @Column(nullable = false, length = 10000) private String content;

    @Setter private String hashtag;

    @CreatedDate @Column(nullable = false) private LocalDateTime createdAt;
    @CreatedBy @Column(nullable = false, length = 100) private String createdBy;
    @LastModifiedDate @Column(nullable = false) private LocalDateTime modifiedAt;
    @LastModifiedBy @Column(nullable = false, length = 100) private String modifiedBy;

    protected Article() {}

    private Article(String title, String content, String hashtag) {
        this.title = title;
        this.content = content;
        this.hashtag = hashtag;
    }

    public static Article of(String title, String content, String hashtag) {
        return new Article(title, content, hashtag);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Article article)) return false; // o가 null 이면 false
        /*
        위 방식은 아래와 같음 (java14+ Pattern matching)
        if (!(o instance of Article)) return false;
        Article article = (Article) o;
         */
        return Objects.nonNull(id) && id.equals(article.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
