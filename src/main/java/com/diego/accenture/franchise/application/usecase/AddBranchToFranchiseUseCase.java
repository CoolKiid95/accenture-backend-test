package com.diego.accenture.franchise.application.usecase;

import com.diego.accenture.franchise.domain.exception.FranchiseNotFoundException;
import com.diego.accenture.franchise.domain.model.Branch;
import com.diego.accenture.franchise.domain.model.Franchise;
import com.diego.accenture.franchise.domain.ports.FranchiseRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.UUID;

@Service
@RequiredArgsConstructor

public class AddBranchToFranchiseUseCase {

    private final FranchiseRepositoryPort franchiseRepositoryPort;

    public Mono<Franchise> execute(String franchiseId, String branchName) {
        return franchiseRepositoryPort.findById(franchiseId)
                .switchIfEmpty(Mono.error(new FranchiseNotFoundException(franchiseId)))
                .flatMap(franchise -> {
                    if (franchise.getBranches() == null){
                        franchise.setBranches(new ArrayList<>());
                    }

                    Branch newBranch = Branch.builder()
                            .id(UUID.randomUUID().toString())
                            .name(branchName)
                            .products(new ArrayList<>())
                            .build();

                    franchise.getBranches().add(newBranch);

                    return franchiseRepositoryPort.save(franchise);

                });
    }
}
