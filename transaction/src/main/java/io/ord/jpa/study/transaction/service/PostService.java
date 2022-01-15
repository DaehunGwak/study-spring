package io.ord.jpa.study.transaction.service;

import io.ord.jpa.study.transaction.domain.Post;
import io.ord.jpa.study.transaction.domain.PostRepository;
import io.ord.jpa.study.transaction.domain.Posts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public Posts savePostsNotTransaction(Posts posts) {
        List<Post> postsResult = postRepository.saveAll(posts.getPosts());
        return new Posts(postsResult);
    }

    @Transactional
    public Posts savePosts(Posts posts) {
        List<Post> postsResult = postRepository.saveAll(posts.getPosts());
        return new Posts(postsResult);
    }
}
