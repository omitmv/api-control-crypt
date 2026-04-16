package com.example.apicontrolcrypt.infrastructure.adapter.in.web;

import com.example.apicontrolcrypt.domain.model.Carteira;
import com.example.apicontrolcrypt.domain.port.in.CarteiraUseCase;
import com.example.apicontrolcrypt.infrastructure.adapter.in.web.dto.carteira.CarteiraResponse;
import com.example.apicontrolcrypt.infrastructure.adapter.in.web.dto.carteira.CreateCarteiraRequest;
import com.example.apicontrolcrypt.infrastructure.adapter.in.web.dto.carteira.UpdateCarteiraRequest;
import com.example.apicontrolcrypt.infrastructure.adapter.in.web.mapper.CarteiraMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/carteiras")
@RequiredArgsConstructor
@Tag(name = "Carteiras", description = "Gerenciamento de carteiras de criptomoedas")
@SecurityRequirement(name = "bearerAuth")
public class CarteiraController {

    private final CarteiraUseCase carteiraUseCase;
    private final CarteiraMapper carteiraMapper;

    @GetMapping
    @Operation(summary = "Listar todas as carteiras", description = "Retorna a lista completa de carteiras cadastradas")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autorizado")
    })
    public ResponseEntity<List<CarteiraResponse>> listarTodas() {
        List<CarteiraResponse> response = carteiraUseCase.listarTodas().stream()
                .map(carteiraMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar carteira por ID", description = "Retorna os dados de uma carteira pelo seu identificador")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Carteira encontrada"),
            @ApiResponse(responseCode = "404", description = "Carteira não encontrada"),
            @ApiResponse(responseCode = "401", description = "Não autorizado")
    })
    public ResponseEntity<CarteiraResponse> buscarPorId(
            @Parameter(description = "ID da carteira", required = true) @PathVariable Integer id) {
        Carteira carteira = carteiraUseCase.buscarPorId(id);
        return ResponseEntity.ok(carteiraMapper.toResponse(carteira));
    }

    @PostMapping
    @Operation(summary = "Criar carteira", description = "Cria uma nova carteira")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Carteira criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "409", description = "Carteira já existe com esta descrição"),
            @ApiResponse(responseCode = "401", description = "Não autorizado")
    })
    public ResponseEntity<CarteiraResponse> criar(@Valid @RequestBody CreateCarteiraRequest request) {
        Carteira carteira = carteiraUseCase.criar(carteiraMapper.toDomain(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(carteiraMapper.toResponse(carteira));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar carteira", description = "Atualiza os dados de uma carteira existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Carteira atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Carteira não encontrada"),
            @ApiResponse(responseCode = "409", description = "Já existe outra carteira com esta descrição"),
            @ApiResponse(responseCode = "401", description = "Não autorizado")
    })
    public ResponseEntity<CarteiraResponse> atualizar(
            @Parameter(description = "ID da carteira", required = true) @PathVariable Integer id,
            @Valid @RequestBody UpdateCarteiraRequest request) {
        Carteira existing = carteiraUseCase.buscarPorId(id);
        Carteira updated = carteiraUseCase.atualizar(id, carteiraMapper.toDomain(request, existing));
        return ResponseEntity.ok(carteiraMapper.toResponse(updated));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar carteira", description = "Remove uma carteira pelo seu identificador")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Carteira removida com sucesso"),
            @ApiResponse(responseCode = "404", description = "Carteira não encontrada"),
            @ApiResponse(responseCode = "401", description = "Não autorizado")
    })
    public ResponseEntity<Void> deletar(
            @Parameter(description = "ID da carteira", required = true) @PathVariable Integer id) {
        carteiraUseCase.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
