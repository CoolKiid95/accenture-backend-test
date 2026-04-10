package com.diego.accenture.franchise.infrastructure.entrypoints.rest;

import com.diego.accenture.franchise.application.dto.AddBranchRequest;
import com.diego.accenture.franchise.application.dto.CreateFranchiseRequest;
import com.diego.accenture.franchise.application.dto.FranchiseResponse;
import com.diego.accenture.franchise.application.mapper.FranchiseDtoMapper;
import com.diego.accenture.franchise.application.usecase.CreateFranchiseUseCase;
import com.diego.accenture.franchise.application.usecase.AddBranchToFranchiseUseCase;

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
}
