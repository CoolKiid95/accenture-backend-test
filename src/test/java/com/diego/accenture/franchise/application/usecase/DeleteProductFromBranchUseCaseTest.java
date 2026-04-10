package com.diego.accenture.franchise.application.usecase;

import com.diego.accenture.franchise.domain.exception.ProductNotFoundException;
import com.diego.accenture.franchise.domain.model.Branch;
import com.diego.accenture.franchise.domain.model.Franchise;
import com.diego.accenture.franchise.domain.model.Product;
import com.diego.accenture.franchise.domain.ports.FranchiseRepositoryPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DeleteProductFromBranchUseCaseTest {

    @Mock
    private FranchiseRepositoryPort franchiseRepositoryPort;

    @InjectMocks
    private DeleteProductFromBranchUseCase deleteProductFromBranchUseCase;

    @Test
    void shouldDeleteProductSuccessfully() {
        Product product = Product.builder()
                .id("p1")
                .name("Coca Cola")
                .stock(20)
                .build();

        Branch branch = Branch.builder()
                .id("b1")
                .name("Sucursal Centro")
                .products(new ArrayList<>(List.of(product)))
                .build();

        Franchise franchise = Franchise.builder()
                .id("f1")
                .name("Franquicia Test")
                .branches(new ArrayList<>(List.of(branch)))
                .build();

        when(franchiseRepositoryPort.findById("f1")).thenReturn(Mono.just(franchise));
        when(franchiseRepositoryPort.save(any(Franchise.class)))
                .thenAnswer(invocation -> Mono.just(invocation.getArgument(0)));

        Mono<Franchise> result = deleteProductFromBranchUseCase.execute("f1", "b1", "p1");

        StepVerifier.create(result)
                .assertNext(updatedFranchise -> {
                    Branch updatedBranch = updatedFranchise.getBranches().get(0);
                    assertTrue(updatedBranch.getProducts().isEmpty());
                })
                .verifyComplete();
    }

    @Test
    void shouldReturnErrorWhenProductDoesNotExist() {
        Branch branch = Branch.builder()
                .id("b1")
                .name("Sucursal Centro")
                .products(new ArrayList<>())
                .build();

        Franchise franchise = Franchise.builder()
                .id("f1")
                .name("Franquicia Test")
                .branches(new ArrayList<>(List.of(branch)))
                .build();

        when(franchiseRepositoryPort.findById("f1")).thenReturn(Mono.just(franchise));

        Mono<Franchise> result = deleteProductFromBranchUseCase.execute("f1", "b1", "p1");

        StepVerifier.create(result)
                .expectError(ProductNotFoundException.class)
                .verify();
    }
}