package io.ordi.jpabook.jpashop;

import lombok.*;

/**
 * lombok test
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Hello {

    private String data;

    public static void main(String[] args) {
        Hello hello = new Hello();
        hello.setData("something");
        System.out.println(hello);
    }
}
