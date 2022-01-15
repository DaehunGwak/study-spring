package io.ord.jpa.study.transaction.web;

import io.ord.jpa.study.JpaApplication;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = JpaApplication.class)
class PostControllerTest {

    @Autowired
    private PostController postController;

    /**
     * Transactional -> PROPAGATION_REQUIRED,ISOLATION_DEFAULT 설정 시
     *
     * 상위에 트랜잭션이 잡혀있는데 하위 레이어에서 트랜잭션이 선언되어 있다면
     * 하위의 트랜잭션은 상위의 트랜잭션에 참여하는 구조
     */
    @Test
    public void savePosts_트랜잭션_테스트() {
        postController.savePosts();
    }
}
