package io.ordi.study.attdorder.application;

import io.ordi.study.attdorder.domain.PendingOrder;
import io.ordi.study.attdorder.domain.PendingOrderRepository;
import io.ordi.study.attdorder.infra.PendingOrderRepositoryMemoryImpl;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CreateOrderServiceTest {

    private final PendingOrderRepository pendingOrderRepository = new PendingOrderRepositoryMemoryImpl();
    private final CreateOrderService createOrderService = new CreateOrderServiceImpl(pendingOrderRepository);

    @Test
    void createPendingOrder() {
        long productId = 1L;
        int quantity = 2;
        PendingOrderRequest request = PendingOrderRequest.of(productId, quantity);

        PendingOrder pendingOrder = createOrderService.createPendingOrder(request);
        
        assertThat(pendingOrder.getId()).isPositive();
    }

}
