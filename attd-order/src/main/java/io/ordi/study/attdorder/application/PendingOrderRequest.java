package io.ordi.study.attdorder.application;

import lombok.Getter;

@Getter
public class PendingOrderRequest {
    private long productId;
    private int quantity;

    public PendingOrderRequest(long productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public static PendingOrderRequest of(long productId, int quantity) {
        return new PendingOrderRequest(productId, quantity);
    }
}
