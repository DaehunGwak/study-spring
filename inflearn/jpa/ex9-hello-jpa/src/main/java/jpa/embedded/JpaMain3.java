package jpa.embedded;

import jpa.embedded.Address;

public class JpaMain3 {

    public static void main(String[] args) {

        Address address1 = new Address("dagu", "dongu", "384");
        Address address2 = new Address("dagu", "dongu", "384");

        System.out.println(address1 == address2);
        System.out.println(address1.equals(address2));

    }
}
