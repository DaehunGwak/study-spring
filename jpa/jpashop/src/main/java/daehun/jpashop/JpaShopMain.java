package daehun.jpashop;

import daehun.jpashop.domain.Member;
import daehun.jpashop.domain.Order;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaShopMain {

    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("jpashop");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        try {

            Order order = entityManager.find(Order.class, 1L);
            Long memberId = order.getMemberId();

            Member member = entityManager.find(Member.class, memberId);
            // 아래처럼 하는게 더 바람직
            // Member orderMEmber = order.getMember();

            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        } finally {
            entityManager.close();
        }

        entityManagerFactory.close();
    }
}
