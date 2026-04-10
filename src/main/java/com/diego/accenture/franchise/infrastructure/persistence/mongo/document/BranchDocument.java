package com.diego.accenture.franchise.infrastructure.persistence.mongo.document;

import com.diego.accenture.franchise.domain.model.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class BranchDocument {
    private String id;
    private String name;

    @Builder.Default
    private List<ProductDocument> products = new ArrayList<>();
}
