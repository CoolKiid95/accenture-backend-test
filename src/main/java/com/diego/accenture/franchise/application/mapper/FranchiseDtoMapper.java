package com.diego.accenture.franchise.application.mapper;

import com.diego.accenture.franchise.application.dto.BranchResponse;
import com.diego.accenture.franchise.application.dto.FranchiseResponse;
import com.diego.accenture.franchise.application.dto.ProductResponse;
import com.diego.accenture.franchise.domain.model.Branch;
import com.diego.accenture.franchise.domain.model.Franchise;
import com.diego.accenture.franchise.domain.model.Product;
import com.diego.accenture.franchise.infrastructure.persistence.mongo.adapter.FranchisePersistenceMapper;

import java.util.List;

public class FranchiseDtoMapper {
    public static FranchiseResponse toResponse(Franchise franchise){
        return new FranchiseResponse(
                franchise.getId(),
                franchise.getName(),
                franchise.getBranches() == null ? List.of() :
                        franchise.getBranches().stream().map(FranchiseDtoMapper::toResponse).toList()
        );
    }

    public static BranchResponse toResponse(Branch branch){
        return new BranchResponse(
                branch.getId(),
                branch.getName(),
                branch.getProducts() == null ? List.of():
                        branch.getProducts().stream().map(FranchiseDtoMapper::toResponse).toList()
        );
    }

    public static ProductResponse toResponse(Product product){
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getStock()
        );
    }
}
