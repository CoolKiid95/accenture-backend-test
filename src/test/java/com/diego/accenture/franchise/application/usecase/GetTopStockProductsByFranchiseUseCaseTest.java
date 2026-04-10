package com.diego.accenture.franchise.application.usecase;

import com.diego.accenture.franchise.application.dto.TopStockProductResponse;
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

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class GetTopStockProductsByFranchiseUseCaseTest {

    @Mock
    private FranchiseRepositoryPort franchiseRepositoryPort;

    @InjectMocks
    private GetTopStockProductsByFranchiseUseCase getTopStockProductsByFranchiseUseCase;

    @Test
    void shouldReturnTopStockProductPerBranch() {
        Branch branch1 = Branch.builder()
                .id("b1")
                .name("Sucursal Centro")
                .products(new ArrayList<>(List.of(
                        Product.builder().id("p1").name("Coca Cola").stock(20).build(),
                        Product.builder().id("p2").name("Papas").stock(50).build()
                )))
                .build();

        Branch branch2 = Branch.builder()
                .id("b2")
                .name("Sucursal Norte")
                .products(new ArrayList<>(List.of(
                        Product.builder().id("p3").name("Agua").stock(30).build(),
                        Product.builder().id("p4").name("Galletas").stock(15).build()
                )))
                .build();

        Franchise franchise = Franchise.builder()
                .id("f1")
                .name("Franquicia Test")
                .branches(new ArrayList<>(List.of(branch1, branch2)))
                .build();

        when(franchiseRepositoryPort.findById("f1")).thenReturn(Mono.just(franchise));

        Mono<List<TopStockProductResponse>> result =
                getTopStockProductsByFranchiseUseCase.execute("f1");

        StepVerifier.create(result)
                .assertNext(topProducts -> {
                    assertEquals(2, topProducts.size());

                    assertEquals("b1", topProducts.get(0).branchId());
                    assertEquals("Papas", topProducts.get(0).productName());
                    assertEquals(50, topProducts.get(0).stock());

                    assertEquals("b2", topProducts.get(1).branchId());
                    assertEquals("Agua", topProducts.get(1).productName());
                    assertEquals(30, topProducts.get(1).stock());
                })
                .verifyComplete();
    }
}