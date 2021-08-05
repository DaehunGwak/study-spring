package daehun.jpa.hello.advancedmapping.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "AM_MOVIE")
@DiscriminatorValue("M") // DTYPE 에 들어갈 데이터 이름
@Getter
@Setter
@NoArgsConstructor
public class Movie extends Item {

    private String director;
    private String actor;

}
