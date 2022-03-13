package io.ordi.jpabook.jpashop.repository.query.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class OrderQueryRepository {

    private final EntityManager em;

    // N+1 문제가 있음
    public List<OrderQueryDto> findOrderQueryDto() {
        List<OrderQueryDto> orders = findOrders(); // query 1번
        orders.forEach(o -> o.setOrderItems(findOrderItems(o.getOrderId()))); // query N번
        return orders;
    }

    private List<OrderQueryDto> findOrders() {
        return em.createQuery(
                        "select new io.ordi.jpabook.jpashop.repository.query.order.OrderQueryDto(o.id, m.name, o.orderDate, o.status, d.address)" +
                                " from Order o" +
                                " join o.member m" +
                                " join o.delivery d", OrderQueryDto.class)
                .getResultList();
    }

    private List<OrderItemQueryDto> findOrderItems(Long orderId) {
        return em.createQuery("select new io.ordi.jpabook.jpashop.repository.query.order.OrderItemQueryDto(oi.order.id, oi.item.name, oi.orderPrice, oi.count)" +
                        " from OrderItem oi" +
                        " join oi.item i" +
                        " where oi.order.id = :orderId", OrderItemQueryDto.class)
                .setParameter("orderId", orderId)
                .getResultList();
    }
}
