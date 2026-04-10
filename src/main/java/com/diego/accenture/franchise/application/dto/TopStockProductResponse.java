package com.diego.accenture.franchise.application.dto;

public record TopStockProductResponse(
        String branchId,
        String branchName,
        String productId,
        String productName,
        Integer stock
) {

}
