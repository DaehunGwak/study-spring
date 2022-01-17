package io.ordi.study.attdorder.application;

import io.ordi.study.attdorder.domain.PendingOrder;
import io.ordi.study.attdorder.domain.PendingOrderRepository;
import io.ordi.study.attdorder.infra.PendingOrderRepositoryMemoryImpl;
import org.springframework.stereotype.Service;

@Service
public class CreateOrderServiceImpl implements CreateOrderService {
    private final PendingOrderRepository pendingOrderRepository;

    public CreateOrderServiceImpl(PendingOrderRepository pendingOrderRepository) {
        this.pendingOrderRepository = pendingOrderRepository;
    }

    @Override
    public PendingOrder createPendingOrder(PendingOrderRequest request) {
        PendingOrder pendingOrder = new PendingOrder(request.getProductId(), request.getQuantity());
        return pendingOrderRepository.save(pendingOrder);
    }

}
