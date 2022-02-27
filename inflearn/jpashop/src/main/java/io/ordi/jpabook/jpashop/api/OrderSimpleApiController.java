package io.ordi.jpabook.jpashop.api;

import io.ordi.jpabook.jpashop.domain.Address;
import io.ordi.jpabook.jpashop.domain.Order;
import io.ordi.jpabook.jpashop.domain.OrderStatus;
import io.ordi.jpabook.jpashop.repository.OrderRepository;
import io.ordi.jpabook.jpashop.repository.OrderSearch;
import io.ordi.jpabook.jpashop.repository.simplequery.OrderSimpleQueryDto;
import io.ordi.jpabook.jpashop.repository.simplequery.OrderSimpleQueryRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
public class OrderSimpleApiController {

    private final OrderRepository orderRepository;
    private final OrderSimpleQueryRepository orderSimpleQueryRepository;

    @Deprecated
    @GetMapping("api/v1/simple-orders")
    public List<Order> ordersV1() {
        List<Order> orders = orderRepository.findAll(new OrderSearch());
//        for (Order order: orders) {
//            order.getMember().getName();
//            order.getDelivery().getAddress();
//        }
        return orders;
    }

    @GetMapping("api/v2/simple-orders")
    public List<SimpleOrderResponse> ordersV2() {
        return orderRepository.findAll(new OrderSearch())
                .stream()
                .map(SimpleOrderResponse::new)
                .collect(Collectors.toList());
    }

    @GetMapping("api/v3/simple-orders")
    public List<SimpleOrderResponse> ordersV3() {
        return orderRepository.findAllWithMemberDelivery()
                .stream()
                .map(SimpleOrderResponse::new)
                .collect(Collectors.toList());
    }

    @GetMapping("api/v4/simple-orders")
    public List<OrderSimpleQueryDto> ordersV4() {
        return orderSimpleQueryRepository.findOrderDtos();
    }

    @Data
    static class SimpleOrderResponse {
        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;

        public SimpleOrderResponse(Order order) {
            this.orderId = order.getId();
            this.name = order.getMember().getName(); // LAZY 초기화
            this.orderDate = order.getOrderDate();
            this.orderStatus = order.getStatus();
            this.address = order.getDelivery().getAddress(); // LAZY 초기화
        }
    }
}
