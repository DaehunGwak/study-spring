package jpa.collection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hello");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        try {

            // 값 타입 저장
            Member member = new Member();
            member.setName("test");
            member.setHomeAddress(new Address("hello", "world", "gogo"));

            member.getFavoriteFoods().add("치킨");
            member.getFavoriteFoods().add("족발");
            member.getFavoriteFoods().add("피자");

            member.getAddressHistory().add(new Address("old1", "st", "zip"));
            member.getAddressHistory().add(new Address("old2", "st", "zip"));

            entityManager.persist(member);

            entityManager.flush();
            entityManager.clear();


            // 값 타입 조회 - 지연 로딩
            System.out.println("============START============");
            Member findMember = entityManager.find(Member.class, member.getId());

            System.out.println("============lazy-loading 1============");
            for (String food : findMember.getFavoriteFoods()) {
                System.out.println(food);
            }
            System.out.println("============lazy-loading 2============");
//            for (Address address : findMember.getAddressHistory()) {
//                System.out.printf("%s %s%n", address.getCity(), address.getStreet());
//            }


            // 값 수정
            // homeCity -> newCity (값 타입은 수정이 되면 안됨)
            Address homeAddress = findMember.getHomeAddress();
            findMember.setHomeAddress(new Address("newCity", homeAddress.getStreet(), homeAddress.getZipcode()));

            // 치킨 -> 한식
            findMember.getFavoriteFoods().remove("치킨");
            findMember.getFavoriteFoods().add("한식");

            // equals, hashCode 가 address 에 재대로 구현되어 있어야 함
            // insert 쿼리가 두개 나간다?
            // 컬렉션에 있는 데이터 한꺼번에 insert 됨
            findMember.getAddressHistory().remove(new AddressEntity("old1", "st", "zip"));
            findMember.getAddressHistory().add(new AddressEntity("newCity1", "st", "zip"));

            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        } finally {
            entityManager.close();
        }

        entityManagerFactory.close();
    }
}
