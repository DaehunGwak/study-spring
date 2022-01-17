package io.ordi.study.attdorder.application;

import io.ordi.study.attdorder.domain.PendingOrder;
import org.springframework.stereotype.Service;

@Service
public interface CreateOrderService {
    PendingOrder createPendingOrder(PendingOrderRequest request);
}
