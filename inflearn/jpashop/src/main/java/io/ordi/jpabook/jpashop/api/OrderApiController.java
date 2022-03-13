package io.ordi.jpabook.jpashop.api;

import io.ordi.jpabook.jpashop.domain.Address;
import io.ordi.jpabook.jpashop.domain.Order;
import io.ordi.jpabook.jpashop.domain.OrderItem;
import io.ordi.jpabook.jpashop.domain.OrderStatus;
import io.ordi.jpabook.jpashop.repository.OrderRepository;
import io.ordi.jpabook.jpashop.repository.OrderSearch;
import io.ordi.jpabook.jpashop.repository.query.order.OrderQueryDto;
import io.ordi.jpabook.jpashop.repository.query.order.OrderQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class OrderApiController {

    private final OrderRepository orderRepository;
    private final OrderQueryRepository orderQueryRepository;

    @GetMapping("/v1/orders")
    public List<Order> ordersV1() {
        List<Order> all = orderRepository.findAll(new OrderSearch());
        for (Order order : all) {
            order.getMember().getName();
            order.getDelivery().getAddress();
            order.getOrderItems().stream()
                    .forEach(oi -> oi.getItem().getName());
        }
        return all;
    }

    @GetMapping("/v2/orders")
    public List<OrderDto> ordersV2() {
        List<Order> all = orderRepository.findAll(new OrderSearch());
        return all.stream()
                .map(OrderDto::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/v3/orders")
    public List<OrderDto> ordersV3() {
        List<Order> all = orderRepository.findAllWithItem();
        return all.stream()
                .map(OrderDto::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/v3.1/orders")
    public List<OrderDto> ordersV3Page(@RequestParam(value = "offset", defaultValue = "0") int offset,
                                       @RequestParam(value = "limit", defaultValue = "100") int limit) {
        List<Order> all = orderRepository.findAllWithMemberDelivery(offset, limit);
        return all.stream()
                .map(OrderDto::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/v4/orders")
    public List<OrderQueryDto> ordersV4() {
        return orderQueryRepository.findOrderQueryDto();
    }

    @Value
    static class OrderDto {
        Long orderId;
        String name;
        LocalDateTime orderDate;
        OrderStatus orderStatus;
        AddressDto address;
        List<OrderItemDto> orderItems;

        public OrderDto(Order o) {
            this.orderId = o.getId();
            this.name = o.getMember().getName();
            this.orderDate = o.getOrderDate();
            this.orderStatus = o.getStatus();
            this.address = Optional.ofNullable(o.getDelivery().getAddress())
                    .map(AddressDto::new)
                    .orElse(null);
            this.orderItems = o.getOrderItems().stream()
                    .map(OrderItemDto::new)
                    .collect(Collectors.toList());
        }
    }

    @Value
    static class AddressDto {
        String city;
        String street;
        String zipcode;

        public AddressDto(Address address) {
            this.city = address.getCity();
            this.street = address.getStreet();
            this.zipcode = address.getZipcode();
        }
    }

    @Value
    static class OrderItemDto {
        String itemName;
        int orderPrice;
        int count;

        public OrderItemDto(OrderItem orderItem) {
            this.itemName = orderItem.getItem().getName();
            this.orderPrice = orderItem.getOrderPrice();
            this.count = orderItem.getCount();
        }
    }
}
