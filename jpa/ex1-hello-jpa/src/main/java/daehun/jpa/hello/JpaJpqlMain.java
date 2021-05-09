package daehun.jpa.hello;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaJpqlMain {

    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hello");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        try {
            // jpql 에서 from에 오는 것은 클래스 명임
            List<Member> resultList = entityManager.createQuery("select m from Member as m", Member.class)
                    .setFirstResult(0)  // 시작은 0 부터
                    .setMaxResults(10)  // pagination
                    .getResultList();

            for (Member m : resultList) {
                System.out.println("m.name = " + m.getName());
            }

            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        } finally {
            entityManager.close();
        }

        entityManagerFactory.close();
    }

}
