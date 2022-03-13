package io.ordi.jpabook.jpashop.repository.query.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    // query 가 2번
    public List<OrderQueryDto> findAllOptimizedByDto() {
        List<OrderQueryDto> orders = findOrders();
        Map<Long, List<OrderItemQueryDto>> orderItemMap = findOrderItemsMap(orders);
        orders.forEach(o -> o.setOrderItems(orderItemMap.get(o.getOrderId())));
        return orders;
    }

    private Map<Long, List<OrderItemQueryDto>> findOrderItemsMap(List<OrderQueryDto> orders) {
        List<Long> orderIds = orders.stream()
                .map(OrderQueryDto::getOrderId)
                .collect(Collectors.toList());

        List<OrderItemQueryDto> orderItems = em.createQuery(
                        "select new io.ordi.jpabook.jpashop.repository.query.order.OrderItemQueryDto(oi.order.id, oi.item.name, oi.orderPrice, oi.count)" +
                                " from OrderItem oi" +
                                " join oi.item i" +
                                " where oi.order.id in :orderIds", OrderItemQueryDto.class)
                .setParameter("orderIds", orderIds)
                .getResultList();

        return orderItems.stream()
                .collect(Collectors.groupingBy(OrderItemQueryDto::getOrderId));
    }

    public List<OrderFlatDto> findAllFlatByDto() {
        return em.createQuery(
                "select new io.ordi.jpabook.jpashop.repository.query.order.OrderFlatDto(" +
                        "       o.id, m.name, o.orderDate, o.status, d.address, i.name, oi.orderPrice, oi.count)" +
                        " from Order o" +
                        " join o.member m" +
                        " join o.delivery d" +
                        " join o.orderItems oi" +
                        " join oi.item i", OrderFlatDto.class)
                .getResultList();
    }
}
