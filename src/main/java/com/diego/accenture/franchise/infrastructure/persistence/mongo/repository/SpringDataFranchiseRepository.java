package com.diego.accenture.franchise.infrastructure.persistence.mongo.repository;

import com.diego.accenture.franchise.infrastructure.persistence.mongo.document.FranchiseDocument;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface SpringDataFranchiseRepository  extends ReactiveMongoRepository<FranchiseDocument, String>{
}
