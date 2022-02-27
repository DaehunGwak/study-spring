package io.ordi.jpabook.jpashop.repository.simplequery;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class OrderSimpleQueryRepository {

    private final EntityManager em;

    public List<OrderSimpleQueryDto> findOrderDtos() {
        return em.createQuery(
                        "select new io.ordi.jpabook.jpashop.repository.simplequery.OrderSimpleQueryDto(" +
                                "       o.id, m.name, o.orderDate, o.status, d.address)" +
                                " from Order o" +
                                " left outer join o.member m" +
                                " left outer join o.delivery d", OrderSimpleQueryDto.class)
                .getResultList();
    }
}
