package com.diego.accenture.franchise.application.usecase;

import com.diego.accenture.franchise.domain.exception.BranchNotFoundException;
import com.diego.accenture.franchise.domain.exception.FranchiseNotFoundException;
import com.diego.accenture.franchise.domain.model.Branch;
import com.diego.accenture.franchise.domain.model.Franchise;
import com.diego.accenture.franchise.domain.ports.FranchiseRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor

public class UpdateBranchNameUseCase {

    private final FranchiseRepositoryPort franchiseRepositoryPort;

    public Mono<Franchise> execute(String franchiseId, String branchId, String newName){
        return franchiseRepositoryPort.findById(franchiseId)
                .switchIfEmpty(Mono.error(new FranchiseNotFoundException(franchiseId)))
                .flatMap(franchise -> {
                    Branch branch = franchise.getBranches()
                            .stream()
                            .filter(b -> b.getId().equals(branchId))
                            .findFirst()
                            .orElseThrow(() -> new BranchNotFoundException(branchId));

                    branch.setName(newName);

                    return franchiseRepositoryPort.save(franchise);
                });
    }
}
