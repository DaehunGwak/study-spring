package io.ordi.study.attdorder.application;

import io.ordi.study.attdorder.domain.PendingOrder;
import lombok.Getter;

@Getter
public class PendingOrderResponse {
    private long id;
    private long productId;
    private int quantity;

    public PendingOrderResponse(long productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public PendingOrderResponse(PendingOrder pendingOrder) {
        this.id = pendingOrder.getId();
        this.productId = pendingOrder.getProductId();
        this.quantity = pendingOrder.getQuantity();
    }
}
