package com.diego.accenture.franchise.application.usecase;

import com.diego.accenture.franchise.domain.exception.BranchNotFoundException;
import com.diego.accenture.franchise.domain.model.Branch;
import com.diego.accenture.franchise.domain.model.Franchise;
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
class AddProductToBranchUseCaseTest {

    @Mock
    private FranchiseRepositoryPort franchiseRepositoryPort;

    @InjectMocks
    private AddProductToBranchUseCase addProductToBranchUseCase;

    @Test
    void shouldAddProductToExistingBranch() {
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
        when(franchiseRepositoryPort.save(any(Franchise.class)))
                .thenAnswer(invocation -> Mono.just(invocation.getArgument(0)));

        Mono<Franchise> result = addProductToBranchUseCase.execute("f1", "b1", "Coca Cola", 20);

        StepVerifier.create(result)
                .assertNext(updatedFranchise -> {
                    Branch updatedBranch = updatedFranchise.getBranches().get(0);
                    assertEquals(1, updatedBranch.getProducts().size());
                    assertEquals("Coca Cola", updatedBranch.getProducts().get(0).getName());
                    assertEquals(20, updatedBranch.getProducts().get(0).getStock());
                })
                .verifyComplete();
    }

    @Test
    void shouldReturnErrorWhenBranchDoesNotExist() {
        Franchise franchise = Franchise.builder()
                .id("f1")
                .name("Franquicia Test")
                .branches(new ArrayList<>())
                .build();

        when(franchiseRepositoryPort.findById("f1")).thenReturn(Mono.just(franchise));

        Mono<Franchise> result = addProductToBranchUseCase.execute("f1", "b1", "Coca Cola", 20);

        StepVerifier.create(result)
                .expectError(BranchNotFoundException.class)
                .verify();
    }
}