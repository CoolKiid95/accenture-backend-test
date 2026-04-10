package com.diego.accenture.franchise.domain.exception;

public class FranchiseNotFoundException extends RuntimeException {
    public FranchiseNotFoundException(String franchiseId) {
        super("Franchise not found with id: " + franchiseId);
    }
}
