package com.fastcampus.projectboard.repository;

import com.fastcampus.projectboard.config.JpaConfig;
import com.fastcampus.projectboard.domain.Article;
import com.fastcampus.projectboard.domain.ArticleComment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("testdb")
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // testdb 에 설정되어 있는 것을 씀
@DisplayName("JPA 연결 테스트")
@Import(JpaConfig.class) // 슬라이스 테스트 시 직접 지정한 auditing 사용 불가, 이렇게 임포트
@DataJpaTest // 내부 보면 Transactional 걸려 있음
class JpaRepositoryTest {

    private final ArticleRepository articleRepository;
    private final ArticleCommentRepository articleCommentRepository;

    private final EntityManager em;

    public JpaRepositoryTest(
            @Autowired ArticleRepository articleRepository,
            @Autowired ArticleCommentRepository articleCommentRepository,
            @Autowired EntityManager em) {
        this.articleRepository = articleRepository;
        this.articleCommentRepository = articleCommentRepository;
        this.em = em;
    }

    @DisplayName("select Article 테스트")
    @Test
    public void selectArticle() throws Exception {
        // when
        List<Article> articles = articleRepository.findAll();

        // then
        assertThat(articles).hasSize(123);
    }

    @DisplayName("insert Article 테스트")
    @Test
    public void insertArticle() throws Exception {
        // given
        long previousCount = articleRepository.count();

        // when
        articleRepository.save(Article.of("test title", "test contents", "test"));

        // then
        assertThat(articleRepository.count()).isEqualTo(previousCount + 1);
    }

    @DisplayName("update Article 테스트")
    @Test
    public void updateArticle() throws Exception {
        // given
        Article article = articleRepository.findById(1L).orElseThrow();
        String updatedHashtag = "#springboot";
        article.setHashtag(updatedHashtag);

        // when
        Article savedArticle = articleRepository.saveAndFlush(article);

        // then
        assertThat(savedArticle).hasFieldOrPropertyWithValue("hashtag", updatedHashtag);
    }

    @DisplayName("delete Article 테스트")
    @Test
    public void deleteArticle() throws Exception {
        // given
        Article targetArticle = articleRepository.findById(1L).orElseThrow();
        long previousArticleCount = articleRepository.count();
        long previousArticleCommentCount = articleCommentRepository.count();
        long targetArticleCommentCount = targetArticle.getArticleComments().size();

        // when
//        articleRepository.delete(targetArticle);

        // only CascadeType.ALL
//        articleCommentRepository.deleteAllInBatch(targetArticle.getArticleComments());
//        targetArticle.getArticleComments().clear();
//        articleRepository.delete(targetArticle);

        // if added orphanRemoval = true
        articleCommentRepository.deleteAllInBatch(targetArticle.getArticleComments());
        em.detach(targetArticle);
        Article targetUpdatedArticle = articleRepository.findById(1L).orElseThrow();
        articleRepository.delete(targetUpdatedArticle);

        // then
        assertThat(articleRepository.count())
                .isEqualTo(previousArticleCount - 1);
        assertThat(articleCommentRepository.count())
                .isEqualTo(previousArticleCommentCount - targetArticleCommentCount);
    }
    
    @DisplayName("batch size 테스트")
    @Test
    public void batchSize() throws Exception {
        // given
        
        // when
        
        // then
    }
}
