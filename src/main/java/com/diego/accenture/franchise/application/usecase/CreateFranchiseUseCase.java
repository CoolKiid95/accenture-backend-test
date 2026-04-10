package com.diego.accenture.franchise.application.usecase;

import com.diego.accenture.franchise.domain.model.Franchise;
import com.diego.accenture.franchise.domain.ports.FranchiseRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class CreateFranchiseUseCase {

    private final FranchiseRepositoryPort franchiseRepositoryPort;

    public Mono<Franchise> execute(String name) {
        Franchise franchise = Franchise.builder()
                .name(name)
                .branches(new ArrayList<>())
                .build();

        return franchiseRepositoryPort.save(franchise);
    }
}
