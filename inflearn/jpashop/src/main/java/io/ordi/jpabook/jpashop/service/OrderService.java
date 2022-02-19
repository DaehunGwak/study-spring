package io.ordi.jpabook.jpashop.service;

import io.ordi.jpabook.jpashop.domain.Delivery;
import io.ordi.jpabook.jpashop.domain.Member;
import io.ordi.jpabook.jpashop.domain.Order;
import io.ordi.jpabook.jpashop.domain.OrderItem;
import io.ordi.jpabook.jpashop.domain.item.Item;
import io.ordi.jpabook.jpashop.repository.ItemRepository;
import io.ordi.jpabook.jpashop.repository.MemberRepository;
import io.ordi.jpabook.jpashop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    // 주문
    @Transactional
    public Long order(Long memberId, Long itemId, int count) {
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);
        Order order = Order.createOrder(member, delivery, orderItem);

        orderRepository.save(order); // order 를 persist 하면 CascadeType.ALL 이라 OrderItem, Delivery 도 같이 저장한다
        return order.getId();
    }

    // 취소
    @Transactional
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findOne(orderId);
        order.cancel();
    }

    // 검색

}
