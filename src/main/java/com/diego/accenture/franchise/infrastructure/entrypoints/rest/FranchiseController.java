package com.diego.accenture.franchise.infrastructure.entrypoints.rest;

import com.diego.accenture.franchise.application.dto.*;
import com.diego.accenture.franchise.application.mapper.FranchiseDtoMapper;
import com.diego.accenture.franchise.application.usecase.CreateFranchiseUseCase;
import com.diego.accenture.franchise.application.usecase.AddBranchToFranchiseUseCase;
import com.diego.accenture.franchise.application.usecase.AddProductToBranchUseCase;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/franchises")
@RequiredArgsConstructor
public class FranchiseController {

    private final CreateFranchiseUseCase createFranchiseUseCase;
    private final AddBranchToFranchiseUseCase addBranchToFranchiseUseCase;
    private final AddProductToBranchUseCase addProductToBranchUseCase;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<FranchiseResponse> createFranchise(@Valid @RequestBody CreateFranchiseRequest request){
        return createFranchiseUseCase.execute(request.name())
                .map(FranchiseDtoMapper::toResponse);
    }

    @PostMapping("/{franchiseId}/branches")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<FranchiseResponse> addBranch(
            @PathVariable String franchiseId,
            @Valid @RequestBody AddBranchRequest request
    ) {
        return addBranchToFranchiseUseCase.execute(franchiseId, request.name())
                .map(FranchiseDtoMapper::toResponse);
    }

    @PostMapping("/{franchiseId}/branches/{branchId}/products")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<FranchiseResponse> addProduct(
            @PathVariable String franchiseId,
            @PathVariable String branchId,
            @Valid @RequestBody AddProductRequest request
            ) {
        return addProductToBranchUseCase.execute(
                franchiseId,
                branchId,
                request.name(),
                request.stock()
        )
                .map(FranchiseDtoMapper::toResponse);
    }
}
