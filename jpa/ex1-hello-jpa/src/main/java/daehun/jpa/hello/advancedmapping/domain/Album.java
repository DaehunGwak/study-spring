package daehun.jpa.hello.advancedmapping.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "AM_ALBUM")
@Getter
@Setter
@NoArgsConstructor
public class Album extends Item {

    private String author;
    private String isbn;

}
