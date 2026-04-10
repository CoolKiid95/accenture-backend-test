package com.diego.accenture.franchise.application.usecase;

import com.diego.accenture.franchise.domain.model.Franchise;
import com.diego.accenture.franchise.domain.ports.FranchiseRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateFranchiseUseCaseTest {

    @Mock
    private FranchiseRepositoryPort franchiseRepositoryPort;

    @InjectMocks
    private CreateFranchiseUseCase createFranchiseUseCase;

    @Test
    void shouldCreateFranchiseSuccessfully(){
        Franchise savedFranchise = Franchise.builder()
                .id("f1")
                .name("Franquicia Test")
                .branches(new ArrayList<>())
                .build();

        when(franchiseRepositoryPort.save(any(Franchise.class)))
                .thenReturn(Mono.just(savedFranchise));

        Mono<Franchise> result = createFranchiseUseCase.execute("Franquicia Test");

        StepVerifier.create(result)
                .assertNext(franchise -> {
                    assertEquals("f1", franchise.getId());
                    assertEquals("Franquicia Test", franchise.getName());
                    assertNotNull(franchise.getBranches());
                    assertTrue(franchise.getBranches().isEmpty());
                })
                .verifyComplete();

        ArgumentCaptor<Franchise> captor = ArgumentCaptor.forClass(Franchise.class);
        verify(franchiseRepositoryPort).save(captor.capture());

        Franchise franchiseToSave = captor.getValue();
        assertEquals("Franquicia Test", franchiseToSave.getName());
        assertNotNull(franchiseToSave.getBranches());
        assertTrue(franchiseToSave.getBranches().isEmpty());

    }
}
