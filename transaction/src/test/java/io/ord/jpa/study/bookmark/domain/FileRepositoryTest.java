package io.ord.jpa.study.bookmark.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("test")
class FileRepositoryTest {

    @Autowired
    private FileRepository fileRepository;

    @BeforeEach
    void setUp() {
        fileRepository.save(new File("sample"));
    }

    @Test
    void findAll() {
        List<File> files = fileRepository.findAll();

        assertThat(files).hasSizeGreaterThan(0);
    }
}
