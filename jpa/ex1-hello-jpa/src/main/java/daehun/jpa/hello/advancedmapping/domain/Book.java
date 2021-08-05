package daehun.jpa.hello.advancedmapping.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "AM_BOOK")
@Getter
@Setter
@NoArgsConstructor
public class Book extends Item {

    private String artist;
}
