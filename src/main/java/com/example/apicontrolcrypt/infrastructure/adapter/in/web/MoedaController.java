package com.example.apicontrolcrypt.infrastructure.adapter.in.web;

import com.example.apicontrolcrypt.domain.model.Moeda;
import com.example.apicontrolcrypt.domain.port.in.MoedaUseCase;
import com.example.apicontrolcrypt.infrastructure.adapter.in.web.dto.moeda.CreateMoedaRequest;
import com.example.apicontrolcrypt.infrastructure.adapter.in.web.dto.moeda.MoedaResponse;
import com.example.apicontrolcrypt.infrastructure.adapter.in.web.dto.moeda.UpdateMoedaRequest;
import com.example.apicontrolcrypt.infrastructure.adapter.in.web.mapper.MoedaMapper;
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
@RequestMapping("/api/moedas")
@RequiredArgsConstructor
@Tag(name = "Moedas", description = "Gerenciamento de moedas/criptoativos")
@SecurityRequirement(name = "bearerAuth")
public class MoedaController {

    private final MoedaUseCase moedaUseCase;
    private final MoedaMapper moedaMapper;

    @GetMapping
    @Operation(summary = "Listar todas as moedas", description = "Retorna a lista completa de moedas cadastradas")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autorizado")
    })
    public ResponseEntity<List<MoedaResponse>> listarTodas() {
        List<MoedaResponse> response = moedaUseCase.listarTodas().stream()
                .map(moedaMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar moeda por ID", description = "Retorna os dados de uma moeda pelo seu identificador")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Moeda encontrada"),
            @ApiResponse(responseCode = "404", description = "Moeda não encontrada"),
            @ApiResponse(responseCode = "401", description = "Não autorizado")
    })
    public ResponseEntity<MoedaResponse> buscarPorId(
            @Parameter(description = "ID da moeda", required = true) @PathVariable Integer id) {
        return ResponseEntity.ok(moedaMapper.toResponse(moedaUseCase.buscarPorId(id)));
    }

    @GetMapping("/carteira/{idCarteira}")
    @Operation(summary = "Listar moedas por carteira", description = "Retorna todas as moedas associadas a uma carteira")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autorizado")
    })
    public ResponseEntity<List<MoedaResponse>> listarPorCarteira(
            @Parameter(description = "ID da carteira", required = true) @PathVariable Integer idCarteira) {
        List<MoedaResponse> response = moedaUseCase.listarPorCarteira(idCarteira).stream()
                .map(moedaMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @Operation(summary = "Criar moeda", description = "Cria uma nova moeda associada a uma carteira")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Moeda criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Carteira não encontrada"),
            @ApiResponse(responseCode = "409", description = "Moeda já existe com esta descrição ou sigla"),
            @ApiResponse(responseCode = "401", description = "Não autorizado")
    })
    public ResponseEntity<MoedaResponse> criar(@Valid @RequestBody CreateMoedaRequest request) {
        Moeda moeda = moedaUseCase.criar(moedaMapper.toDomain(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(moedaMapper.toResponse(moeda));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar moeda", description = "Atualiza os dados de uma moeda existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Moeda atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Moeda ou carteira não encontrada"),
            @ApiResponse(responseCode = "409", description = "Já existe outra moeda com esta descrição ou sigla"),
            @ApiResponse(responseCode = "401", description = "Não autorizado")
    })
    public ResponseEntity<MoedaResponse> atualizar(
            @Parameter(description = "ID da moeda", required = true) @PathVariable Integer id,
            @Valid @RequestBody UpdateMoedaRequest request) {
        Moeda existing = moedaUseCase.buscarPorId(id);
        Moeda updated = moedaUseCase.atualizar(id, moedaMapper.toDomain(request, existing));
        return ResponseEntity.ok(moedaMapper.toResponse(updated));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar moeda", description = "Remove uma moeda pelo seu identificador")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Moeda removida com sucesso"),
            @ApiResponse(responseCode = "404", description = "Moeda não encontrada"),
            @ApiResponse(responseCode = "401", description = "Não autorizado")
    })
    public ResponseEntity<Void> deletar(
            @Parameter(description = "ID da moeda", required = true) @PathVariable Integer id) {
        moedaUseCase.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
