package io.ord.jpa.study.transaction.web;

import io.ord.jpa.study.transaction.domain.Post;
import io.ord.jpa.study.transaction.domain.Posts;
import io.ord.jpa.study.transaction.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RequiredArgsConstructor
@Controller
public class PostController {

    private final PostService postService;

    /**
     * 샘플 save 2개
     */
    @Transactional //
    public void savePosts() {
        Posts posts = new Posts(IntStream.range(0, 2)
                .mapToObj(i -> new Post())
                .collect(Collectors.toList()));
        postService.savePosts(posts);
    }
}
