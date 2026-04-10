package com.diego.accenture.franchise.infrastructure.entrypoints.rest;

import com.diego.accenture.franchise.application.dto.*;
import com.diego.accenture.franchise.application.mapper.FranchiseDtoMapper;
import com.diego.accenture.franchise.application.usecase.CreateFranchiseUseCase;
import com.diego.accenture.franchise.application.usecase.AddBranchToFranchiseUseCase;
import com.diego.accenture.franchise.application.usecase.AddProductToBranchUseCase;
import com.diego.accenture.franchise.application.usecase.DeleteProductFromBranchUseCase;
import com.diego.accenture.franchise.application.dto.UpdateStockRequest;
import com.diego.accenture.franchise.application.usecase.UpdateProductStockUseCase;
import com.diego.accenture.franchise.application.dto.TopStockProductResponse;
import com.diego.accenture.franchise.application.usecase.GetTopStockProductsByFranchiseUseCase;
import com.diego.accenture.franchise.application.dto.UpdateNameRequest;
import com.diego.accenture.franchise.application.usecase.UpdateBranchNameUseCase;
import com.diego.accenture.franchise.application.usecase.UpdateFranchiseNameUseCase;
import com.diego.accenture.franchise.application.usecase.UpdateProductNameUseCase;

import com.diego.accenture.franchise.domain.model.Franchise;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import java.util.List;

@RestController
@RequestMapping("/api/franchises")
@RequiredArgsConstructor
public class FranchiseController {

    private final CreateFranchiseUseCase createFranchiseUseCase;
    private final AddBranchToFranchiseUseCase addBranchToFranchiseUseCase;
    private final AddProductToBranchUseCase addProductToBranchUseCase;
    private final DeleteProductFromBranchUseCase deleteProductFromBranchUseCase;
    private final UpdateProductStockUseCase updateProductStockUseCase;
    private final GetTopStockProductsByFranchiseUseCase getTopStockProductsByFranchiseUseCase;
    private final UpdateFranchiseNameUseCase updateFranchiseNameUseCase;
    private final UpdateBranchNameUseCase updateBranchNameUseCase;
    private final UpdateProductNameUseCase updateProductNameUseCase;

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

    @DeleteMapping("{franchiseId}/branches/{branchId}/products/{productId}")
    public Mono<FranchiseResponse> deleteProduct(
            @PathVariable String franchiseId,
            @PathVariable String branchId,
            @PathVariable String productId
    ) {
        return deleteProductFromBranchUseCase.execute(franchiseId, branchId, productId)
                .map(FranchiseDtoMapper::toResponse);
    }

    @PatchMapping("/{franchiseId}/branches/{branchId}/products/{productId}/stock")
    public Mono<FranchiseResponse> updateProductStock(
            @PathVariable("franchiseId") String franchiseId,
            @PathVariable("branchId") String branchId,
            @PathVariable("productId") String productId,
            @Valid @RequestBody UpdateStockRequest request
    ) {
        return updateProductStockUseCase.execute(franchiseId, branchId, productId, request.stock())
                .map(FranchiseDtoMapper::toResponse);
    }

    @GetMapping("/{franchiseId}/top-stock-products")
    public Mono<List<TopStockProductResponse>> getTopStockProductByFranchise(
            @PathVariable("franchiseId") String franchiseId
    ){
        return getTopStockProductsByFranchiseUseCase.execute(franchiseId);
    }

    @PatchMapping("/{franchiseId}/name")
    public Mono<FranchiseResponse> updateFranchiseName(
            @PathVariable("franchiseId") String franchiseId,
            @Valid @RequestBody UpdateNameRequest request
    ){
        return updateFranchiseNameUseCase.execute(franchiseId, request.name())
                .map(FranchiseDtoMapper::toResponse);
    }

    @PatchMapping("/{franchiseId}/branches/{branchId}/name")
    public Mono<FranchiseResponse> updateBranchName(
            @PathVariable("franchiseId") String franchiseId,
            @PathVariable("branchId") String branchId,
            @Valid @RequestBody UpdateNameRequest request
    ){
        return updateBranchNameUseCase.execute(franchiseId, branchId, request.name())
                .map(FranchiseDtoMapper::toResponse);
    }

    @PatchMapping("/{franchiseId}/branches/{branchId}/products/{productId}/name")
    public Mono<FranchiseResponse> updateProductName(
            @PathVariable("franchiseId") String franchiseId,
            @PathVariable("branchId") String branchId,
            @PathVariable("productId") String productId,
            @Valid @RequestBody UpdateNameRequest request
            ){
        return updateProductNameUseCase.execute(franchiseId, branchId, productId, request.name())
                .map(FranchiseDtoMapper::toResponse);
    }
}
