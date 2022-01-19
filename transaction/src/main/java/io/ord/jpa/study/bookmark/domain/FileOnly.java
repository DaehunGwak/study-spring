package io.ord.jpa.study.bookmark.domain;

import org.springframework.data.annotation.Immutable;

import javax.persistence.*;

/**
 * https://thorben-janssen.com/hibernate-tips-map-multiple-entities-same-table/
 * https://www.baeldung.com/hibernate-immutable
 */
@Entity
@Table(name = "file")
@Immutable
public class FileOnly {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
}
