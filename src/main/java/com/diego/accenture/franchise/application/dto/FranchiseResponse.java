package com.diego.accenture.franchise.application.dto;

import java.util.List;

public record FranchiseResponse (
        String id,
        String name,
        List<BranchResponse> branches
) {
}
