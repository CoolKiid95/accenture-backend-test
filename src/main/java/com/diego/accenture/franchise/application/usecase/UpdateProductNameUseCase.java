package com.diego.accenture.franchise.application.usecase;

import com.diego.accenture.franchise.domain.exception.BranchNotFoundException;
import com.diego.accenture.franchise.domain.exception.FranchiseNotFoundException;
import com.diego.accenture.franchise.domain.exception.ProductNotFoundException;
import com.diego.accenture.franchise.domain.model.Branch;
import com.diego.accenture.franchise.domain.model.Franchise;
import com.diego.accenture.franchise.domain.model.Product;
import com.diego.accenture.franchise.domain.ports.FranchiseRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor

public class UpdateProductNameUseCase {

    private final FranchiseRepositoryPort franchiseRepositoryPort;

    public Mono<Franchise> execute(String franchiseId, String branchId, String productId, String newName){
        return franchiseRepositoryPort.findById(franchiseId)
                .switchIfEmpty(Mono.error(new FranchiseNotFoundException(franchiseId)))
                .flatMap(franchise -> {
                    Branch branch = franchise.getBranches()
                            .stream()
                            .filter(b -> b.getId().equals(branchId))
                            .findFirst()
                            .orElseThrow( () -> new BranchNotFoundException(branchId));

                    if (branch.getProducts() == null || branch.getProducts().isEmpty()){
                        throw new ProductNotFoundException(productId);
                    }

                    Product product = branch.getProducts()
                            .stream()
                            .filter(p -> p.getId().equals(productId))
                            .findFirst()
                            .orElseThrow(() -> new ProductNotFoundException(productId));

                    product.setName(newName);

                    return franchiseRepositoryPort.save(franchise);
                });
    }
}
