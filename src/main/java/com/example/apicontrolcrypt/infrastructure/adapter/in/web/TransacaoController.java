package com.example.apicontrolcrypt.infrastructure.adapter.in.web;

import com.example.apicontrolcrypt.domain.model.Transacao;
import com.example.apicontrolcrypt.domain.port.in.TransacaoUseCase;
import com.example.apicontrolcrypt.infrastructure.adapter.in.web.dto.transacao.CreateTransacaoRequest;
import com.example.apicontrolcrypt.infrastructure.adapter.in.web.dto.transacao.TransacaoResponse;
import com.example.apicontrolcrypt.infrastructure.adapter.in.web.dto.transacao.UpdateTransacaoRequest;
import com.example.apicontrolcrypt.infrastructure.adapter.in.web.mapper.TransacaoMapper;
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
@RequestMapping("/api/transacoes")
@RequiredArgsConstructor
@Tag(name = "Transações", description = "Gerenciamento de transações de compra e venda de criptomoedas")
@SecurityRequirement(name = "bearerAuth")
public class TransacaoController {

    private final TransacaoUseCase transacaoUseCase;
    private final TransacaoMapper transacaoMapper;

    @GetMapping
    @Operation(summary = "Listar todas as transações", description = "Retorna a lista completa de transações cadastradas")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autorizado")
    })
    public ResponseEntity<List<TransacaoResponse>> listarTodas() {
        List<TransacaoResponse> response = transacaoUseCase.listarTodas().stream()
                .map(transacaoMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar transação por ID", description = "Retorna os dados de uma transação pelo seu identificador")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Transação encontrada"),
            @ApiResponse(responseCode = "404", description = "Transação não encontrada"),
            @ApiResponse(responseCode = "401", description = "Não autorizado")
    })
    public ResponseEntity<TransacaoResponse> buscarPorId(
            @Parameter(description = "ID da transação", required = true) @PathVariable Integer id) {
        return ResponseEntity.ok(transacaoMapper.toResponse(transacaoUseCase.buscarPorId(id)));
    }

    @GetMapping("/carteira/{idCarteira}")
    @Operation(summary = "Listar transações por carteira", description = "Retorna todas as transações de uma determinada carteira")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autorizado")
    })
    public ResponseEntity<List<TransacaoResponse>> listarPorCarteira(
            @Parameter(description = "ID da carteira", required = true) @PathVariable Integer idCarteira) {
        List<TransacaoResponse> response = transacaoUseCase.listarPorCarteira(idCarteira).stream()
                .map(transacaoMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/moeda/{idMoeda}")
    @Operation(summary = "Listar transações por moeda", description = "Retorna todas as transações de uma determinada moeda")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autorizado")
    })
    public ResponseEntity<List<TransacaoResponse>> listarPorMoeda(
            @Parameter(description = "ID da moeda", required = true) @PathVariable Integer idMoeda) {
        List<TransacaoResponse> response = transacaoUseCase.listarPorMoeda(idMoeda).stream()
                .map(transacaoMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @Operation(summary = "Criar transação", description = "Registra uma nova transação de compra ou venda")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Transação criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Carteira ou moeda não encontrada"),
            @ApiResponse(responseCode = "401", description = "Não autorizado")
    })
    public ResponseEntity<TransacaoResponse> criar(@Valid @RequestBody CreateTransacaoRequest request) {
        Transacao transacao = transacaoUseCase.criar(transacaoMapper.toDomain(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(transacaoMapper.toResponse(transacao));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar transação", description = "Atualiza os dados de uma transação existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Transação atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Transação, carteira ou moeda não encontrada"),
            @ApiResponse(responseCode = "401", description = "Não autorizado")
    })
    public ResponseEntity<TransacaoResponse> atualizar(
            @Parameter(description = "ID da transação", required = true) @PathVariable Integer id,
            @Valid @RequestBody UpdateTransacaoRequest request) {
        Transacao existing = transacaoUseCase.buscarPorId(id);
        Transacao updated = transacaoUseCase.atualizar(id, transacaoMapper.toDomain(request, existing));
        return ResponseEntity.ok(transacaoMapper.toResponse(updated));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar transação", description = "Remove uma transação pelo seu identificador")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Transação removida com sucesso"),
            @ApiResponse(responseCode = "404", description = "Transação não encontrada"),
            @ApiResponse(responseCode = "401", description = "Não autorizado")
    })
    public ResponseEntity<Void> deletar(
            @Parameter(description = "ID da transação", required = true) @PathVariable Integer id) {
        transacaoUseCase.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
