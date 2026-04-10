package com.diego.accenture.franchise.application.usecase;

import com.diego.accenture.franchise.domain.exception.FranchiseNotFoundException;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AddBranchToFranchiseUseCaseTest {

    @Mock
    private FranchiseRepositoryPort franchiseRepositoryPort;

    @InjectMocks
    private AddBranchToFranchiseUseCase addBranchToFranchiseUseCase;

    @Test
    void shouldAddBranchToExistingFranchise(){
        Franchise franchise = Franchise.builder()
                .id("f1")
                .name("Franquicia Test")
                .branches(new ArrayList<>())
                .build();

        when(franchiseRepositoryPort.findById("f1")).thenReturn(Mono.just(franchise));
        when(franchiseRepositoryPort.save(any(Franchise.class)))
                .thenAnswer(invocation -> Mono.just(invocation.getArgument(0)));

        Mono<Franchise> result = addBranchToFranchiseUseCase.execute("f1", "Sucursal Centro");

        StepVerifier.create(result)
                .assertNext(updatedFranchise ->{
                    assertEquals(1, updatedFranchise.getBranches().size());
                    assertEquals("Sucursal Centro", updatedFranchise.getBranches().get(0).getName());
                    assertNotNull(updatedFranchise.getBranches().get(0).getId());
                })
                .verifyComplete();
    }

    @Test
    void shouldReturnErrorWhenFranchiseDoesNotExist(){
        when(franchiseRepositoryPort.findById("f1")).thenReturn(Mono.empty());

        Mono<Franchise> result = addBranchToFranchiseUseCase.execute("f1","Sucursal Centro");

        StepVerifier.create(result)
                .expectError(FranchiseNotFoundException.class)
                .verify();
    }

}
