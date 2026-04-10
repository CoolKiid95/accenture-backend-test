package com.diego.accenture.franchise.domain.exception;

public class BranchNotFoundException extends RuntimeException {
    public BranchNotFoundException(String branchId){
        super("Branch not found with id: " + branchId);
    }
}
