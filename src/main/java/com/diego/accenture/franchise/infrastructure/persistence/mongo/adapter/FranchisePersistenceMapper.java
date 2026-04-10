package com.diego.accenture.franchise.infrastructure.persistence.mongo.adapter;

import com.diego.accenture.franchise.domain.model.Branch;
import com.diego.accenture.franchise.domain.model.Franchise;
import com.diego.accenture.franchise.domain.model.Product;
import com.diego.accenture.franchise.infrastructure.persistence.mongo.document.BranchDocument;
import com.diego.accenture.franchise.infrastructure.persistence.mongo.document.FranchiseDocument;
import com.diego.accenture.franchise.infrastructure.persistence.mongo.document.ProductDocument;

import java.util.ArrayList;
import java.util.List;

public class FranchisePersistenceMapper {
    public static FranchiseDocument toDocument(Franchise franchise) {
        return FranchiseDocument.builder()
                .id(franchise.getId())
                .name(franchise.getName())
                .branches(franchise.getBranches() == null
                        ? new ArrayList<>()
                        : new ArrayList<>(franchise.getBranches().stream()
                                          .map(FranchisePersistenceMapper::toDocument)
                                          .toList()))
                .build();
    }

    public static BranchDocument toDocument(Branch branch) {
        return BranchDocument.builder()
                .id(branch.getId())
                .name(branch.getName())
                .products(branch.getProducts() == null
                ? new ArrayList<>()
                : new ArrayList<>(branch.getProducts().stream()
                                  .map(FranchisePersistenceMapper::toDocument)
                                  .toList()))
                .build();
    }

    public static ProductDocument toDocument(Product product){
        return ProductDocument.builder()
                .id(product.getId())
                .name(product.getName())
                .stock(product.getStock())
                .build();
    }

    public static Franchise toDomain(FranchiseDocument document){
        return Franchise.builder()
                .id(document.getId())
                .name(document.getName())
                .branches(document.getBranches() == null
                        ? new ArrayList<>()
                        : new ArrayList<>(document.getBranches().stream()
                                          .map(FranchisePersistenceMapper::toDomain)
                                          .toList()))
                .build();
    }

    public static Branch toDomain(BranchDocument document){
        return Branch.builder()
                .id(document.getId())
                .name(document.getName())
                .products(document.getProducts() == null
                        ? new ArrayList<>()
                        : new ArrayList<>(document.getProducts().stream()
                                          .map(FranchisePersistenceMapper::toDomain)
                                          .toList()))
                .build();
    }

    public static Product toDomain(ProductDocument document){
        return Product.builder()
                .id(document.getId())
                .name(document.getName())
                .stock(document.getStock())
                .build();
    }
}
