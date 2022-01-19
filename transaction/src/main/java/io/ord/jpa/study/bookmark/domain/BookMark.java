package io.ord.jpa.study.bookmark.domain;

import lombok.Getter;

import javax.persistence.Entity;

@Getter
@Entity
public class BookMark extends File {

    private String url;
}
