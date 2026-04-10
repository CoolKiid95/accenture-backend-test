package com.diego.accenture.franchise.infrastructure.persistence.mongo.adapter;

import com.diego.accenture.franchise.domain.model.Franchise;
import com.diego.accenture.franchise.domain.ports.FranchiseRepositoryPort;
import com.diego.accenture.franchise.infrastructure.persistence.mongo.repository.SpringDataFranchiseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor

public class MongoFranchiseRepositoryAdapter implements FranchiseRepositoryPort {
    private final SpringDataFranchiseRepository repository;

    @Override
    public Mono<Franchise> save(Franchise franchise){
        return repository.save(FranchisePersistenceMapper.toDocument(franchise))
                .map(FranchisePersistenceMapper::toDomain);
    }

    @Override
    public Mono<Franchise> findById(String id){
        return repository.findById(id)
                .map(FranchisePersistenceMapper::toDomain);
    }

    @Override
    public Flux<Franchise> findAll() {
        return repository.findAll()
                .map(FranchisePersistenceMapper::toDomain);
    }
}
