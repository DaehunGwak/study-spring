package io.ordi.jpabook.jpashop.repository;

import io.ordi.jpabook.jpashop.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class OrderRepository {

    private final EntityManager em;

    public void save(Order order) {
        em.persist(order);
    }

    public Order findOne(Long id) {
        return em.find(Order.class, id);
    }

    public List<Order> findAll() {
        return em.createQuery("select o from Order o join o.member m", Order.class)
                .setMaxResults(1000)
                .getResultList();
    }

//    public List<Order> findAllByCriteria()

//    public List<Order> findAll(OrderSearch orderSearch) {
//        // querydsl
//    }
}
