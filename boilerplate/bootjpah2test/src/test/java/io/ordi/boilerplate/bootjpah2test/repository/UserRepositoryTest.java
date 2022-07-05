package io.ordi.boilerplate.bootjpah2test.repository;

import io.ordi.boilerplate.bootjpah2test.repository.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class UserRepositoryTest {

    @Autowired
    private EntityManager em;
    @Autowired
    private UserRepository userRepository;


    @BeforeEach
    public void setUpEach() {
        // given
        User test = User.builder()
                .name("test")
                .build();
        userRepository.saveAndFlush(test);
        em.clear();
    }

    @Test
    public void readOne() {
        // when
        List<User> users = userRepository.findAll();

        // then
        assertThat(users).hasSize(1);
        assertThat(users.get(0).getName()).isEqualTo("test");
    }
}
