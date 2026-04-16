package com.example.apicontrolcrypt.infrastructure.adapter.in.web;

import com.example.apicontrolcrypt.domain.model.Cotacao;
import com.example.apicontrolcrypt.domain.port.in.CotacaoUseCase;
import com.example.apicontrolcrypt.infrastructure.adapter.in.web.dto.cotacao.CotacaoResponse;
import com.example.apicontrolcrypt.infrastructure.adapter.in.web.dto.cotacao.CreateCotacaoRequest;
import com.example.apicontrolcrypt.infrastructure.adapter.in.web.dto.cotacao.UpdateCotacaoRequest;
import com.example.apicontrolcrypt.infrastructure.adapter.in.web.mapper.CotacaoMapper;
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
@RequestMapping("/api/cotacoes")
@RequiredArgsConstructor
@Tag(name = "Cotações", description = "Gerenciamento de cotações de criptomoedas")
@SecurityRequirement(name = "bearerAuth")
public class CotacaoController {

    private final CotacaoUseCase cotacaoUseCase;
    private final CotacaoMapper cotacaoMapper;

    @GetMapping
    @Operation(summary = "Listar todas as cotações", description = "Retorna a lista completa de cotações cadastradas")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autorizado")
    })
    public ResponseEntity<List<CotacaoResponse>> listarTodas() {
        List<CotacaoResponse> response = cotacaoUseCase.listarTodas().stream()
                .map(cotacaoMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar cotação por ID", description = "Retorna os dados de uma cotação pelo seu identificador")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cotação encontrada"),
            @ApiResponse(responseCode = "404", description = "Cotação não encontrada"),
            @ApiResponse(responseCode = "401", description = "Não autorizado")
    })
    public ResponseEntity<CotacaoResponse> buscarPorId(
            @Parameter(description = "ID da cotação", required = true) @PathVariable Integer id) {
        return ResponseEntity.ok(cotacaoMapper.toResponse(cotacaoUseCase.buscarPorId(id)));
    }

    @GetMapping("/carteira/{idCarteira}")
    @Operation(summary = "Listar cotações por carteira", description = "Retorna todas as cotações associadas a uma carteira")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autorizado")
    })
    public ResponseEntity<List<CotacaoResponse>> listarPorCarteira(
            @Parameter(description = "ID da carteira", required = true) @PathVariable Integer idCarteira) {
        List<CotacaoResponse> response = cotacaoUseCase.listarPorCarteira(idCarteira).stream()
                .map(cotacaoMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/moeda/{idMoeda}")
    @Operation(summary = "Listar cotações por moeda", description = "Retorna todas as cotações de uma determinada moeda")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autorizado")
    })
    public ResponseEntity<List<CotacaoResponse>> listarPorMoeda(
            @Parameter(description = "ID da moeda", required = true) @PathVariable Integer idMoeda) {
        List<CotacaoResponse> response = cotacaoUseCase.listarPorMoeda(idMoeda).stream()
                .map(cotacaoMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @Operation(summary = "Criar cotação", description = "Registra uma nova cotação para uma moeda em uma carteira")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Cotação criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Carteira ou moeda não encontrada"),
            @ApiResponse(responseCode = "401", description = "Não autorizado")
    })
    public ResponseEntity<CotacaoResponse> criar(@Valid @RequestBody CreateCotacaoRequest request) {
        Cotacao cotacao = cotacaoUseCase.criar(cotacaoMapper.toDomain(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(cotacaoMapper.toResponse(cotacao));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar cotação", description = "Atualiza os dados de uma cotação existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cotação atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Cotação, carteira ou moeda não encontrada"),
            @ApiResponse(responseCode = "401", description = "Não autorizado")
    })
    public ResponseEntity<CotacaoResponse> atualizar(
            @Parameter(description = "ID da cotação", required = true) @PathVariable Integer id,
            @Valid @RequestBody UpdateCotacaoRequest request) {
        Cotacao existing = cotacaoUseCase.buscarPorId(id);
        Cotacao updated = cotacaoUseCase.atualizar(id, cotacaoMapper.toDomain(request, existing));
        return ResponseEntity.ok(cotacaoMapper.toResponse(updated));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar cotação", description = "Remove uma cotação pelo seu identificador")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Cotação removida com sucesso"),
            @ApiResponse(responseCode = "404", description = "Cotação não encontrada"),
            @ApiResponse(responseCode = "401", description = "Não autorizado")
    })
    public ResponseEntity<Void> deletar(
            @Parameter(description = "ID da cotação", required = true) @PathVariable Integer id) {
        cotacaoUseCase.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
