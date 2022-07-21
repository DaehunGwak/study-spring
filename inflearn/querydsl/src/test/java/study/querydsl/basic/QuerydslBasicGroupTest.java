package study.querydsl.basic;

import com.querydsl.core.Tuple;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class QuerydslBasicGroupTest extends QuerydslBasicTestCore {

    @Test // 집합
    public void aggregation() throws Exception {
        // tuple?? -> DTO 로 직접 뽑기
        Tuple resultTuple = queryFactory.select(
                        qMember.count(),
                        qMember.age.sum(),
                        qMember.age.avg(),
                        qMember.age.min(),
                        qMember.age.max())
                .from(qMember)
                .fetchOne();

        assertThat(resultTuple.get(qMember.count())).isEqualTo(4);
        assertThat(resultTuple.get(qMember.age.sum())).isEqualTo(100);
        assertThat(resultTuple.get(qMember.age.avg())).isEqualTo(25);
        assertThat(resultTuple.get(qMember.age.min())).isEqualTo(10);
        assertThat(resultTuple.get(qMember.age.max())).isEqualTo(40);
    }

    /**
     * 팀 이름과 각 팀 평균 연령을 구해라
     */
    @Test
    public void groupBy() throws Exception {
        // when
        List<Tuple> results = queryFactory.select(qTeam.name, qMember.age.avg())
                .from(qMember)
                .join(qMember.team, qTeam)
                .groupBy(qTeam)
                .fetch();

        // then
        Tuple teamAResult = results.get(0);
        Tuple teamBResult = results.get(1);
        assertThat(teamAResult.get(qTeam.name)).isEqualTo("teamA");
        assertThat(teamAResult.get(qMember.age.avg())).isEqualTo(15);
        assertThat(teamBResult.get(qTeam.name)).isEqualTo("teamB");
        assertThat(teamBResult.get(qMember.age.avg())).isEqualTo(35);
    }
}
