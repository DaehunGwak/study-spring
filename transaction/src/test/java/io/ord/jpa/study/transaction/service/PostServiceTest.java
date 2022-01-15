package io.ord.jpa.study.transaction.service;

import io.ord.jpa.study.JpaApplication;
import io.ord.jpa.study.transaction.domain.Post;
import io.ord.jpa.study.transaction.domain.Posts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = JpaApplication.class)
class PostServiceTest {

    @Autowired
    private PostService postService;

    private Posts mockPosts;

    @BeforeEach
    public void setUp() {
        mockPosts = new Posts(IntStream.range(0, 2)
                .mapToObj(i -> new Post())
                .collect(Collectors.toList()));
    }

    @Test
    public void savePostsNotTransaction_트랜잭션_테스트() {
        postService.savePostsNotTransaction(mockPosts);
    }

    @Test
    public void savePosts_트랜잭션_테스트() {
        postService.savePosts(mockPosts);
    }
}
