package com.diego.accenture.franchise.application.usecase;

import com.diego.accenture.franchise.application.dto.TopStockProductResponse;
import com.diego.accenture.franchise.domain.exception.FranchiseNotFoundException;
import com.diego.accenture.franchise.domain.model.Branch;
import com.diego.accenture.franchise.domain.model.Product;
import com.diego.accenture.franchise.domain.ports.FranchiseRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor

public class GetTopStockProductsByFranchiseUseCase {

    private final FranchiseRepositoryPort franchiseRepositoryPort;

    public Mono<List<TopStockProductResponse>> execute(String franchiseId){
        return franchiseRepositoryPort.findById(franchiseId)
                .switchIfEmpty(Mono.error(new FranchiseNotFoundException(franchiseId)))
                .map(franchise -> franchise.getBranches() == null ? List.<TopStockProductResponse>of() :
                        franchise.getBranches().stream()
                        .filter(branch -> branch.getProducts() != null && !branch.getProducts().isEmpty())
                        .map(this::mapTopProductByBranch)
                        .toList()
                );
    }

    private TopStockProductResponse mapTopProductByBranch(Branch branch){
        Product topProduct = branch.getProducts().stream()
                .max(Comparator.comparing(Product::getStock))
                .orElseThrow();

        return new TopStockProductResponse(
                branch.getId(),
                branch.getName(),
                topProduct.getId(),
                topProduct.getName(),
                topProduct.getStock()
        );
    }
}
