package io.ord.jpa.study.bookmark.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Inheritance(strategy = InheritanceType.JOINED)
@Entity
public class File {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    public File(String name) {
        this.name = name;
    }
}
