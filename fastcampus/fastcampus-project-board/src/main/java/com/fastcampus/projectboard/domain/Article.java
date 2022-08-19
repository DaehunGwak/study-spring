package com.fastcampus.projectboard.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@ToString
@Table(indexes = {
        @Index(columnList = "title", name = "idx_article_title"),
        @Index(columnList = "hashtag", name = "idx_article_hashtag"),
        @Index(columnList = "createdAt", name = "idx_article_created_at"),
        @Index(columnList = "createdBy", name = "idx_article_created_by")
})
@Entity
public class Article extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(nullable = false)
    private String title;
    @Setter
    @Column(nullable = false, length = 10000)
    private String content;

    @Setter
    private String hashtag;

    @OrderBy("id")
//    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL) // foreign key cascade 는 공부 목적, 팀바팀 사바사
//    @OneToMany(mappedBy = "article", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private final Set<ArticleComment> articleComments = new LinkedHashSet<>();
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
