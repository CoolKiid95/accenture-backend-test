package com.diego.accenture.franchise.application.usecase;

import com.diego.accenture.franchise.domain.exception.BranchNotFoundException;
import com.diego.accenture.franchise.domain.exception.FranchiseNotFoundException;
import com.diego.accenture.franchise.domain.model.Branch;
import com.diego.accenture.franchise.domain.model.Franchise;
import com.diego.accenture.franchise.domain.model.Product;
import com.diego.accenture.franchise.domain.ports.FranchiseRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.UUID;

@Service
@RequiredArgsConstructor

public class AddProductToBranchUseCase {

    private final FranchiseRepositoryPort franchiseRepositoryPort;

    public Mono<Franchise> execute(String franchiseId, String branchId, String productName, Integer stock){
        return franchiseRepositoryPort.findById(franchiseId)
                .switchIfEmpty(Mono.error(new FranchiseNotFoundException(franchiseId)))
                .flatMap(franchise -> {
                    Branch branch = franchise.getBranches()
                            .stream()
                            .filter(b -> b.getId().equals(branchId))
                            .findFirst()
                            .orElseThrow(()-> new BranchNotFoundException(branchId));

                    if (branch.getProducts() == null) {
                        branch.setProducts(new ArrayList<>());
                    }

                    Product newProduct = Product.builder()
                            .id(UUID.randomUUID().toString())
                            .name(productName)
                            .stock(stock)
                            .build();

                    branch.getProducts().add(newProduct);

                    return franchiseRepositoryPort.save(franchise);
                });
    }
}
