package io.ord.jpa.study.transaction.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class Posts {

    private final List<Post> posts;
}
