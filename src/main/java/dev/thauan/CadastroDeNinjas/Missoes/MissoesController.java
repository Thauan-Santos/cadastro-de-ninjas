package dev.thauan.CadastroDeNinjas.Missoes;

import dev.thauan.CadastroDeNinjas.Ninjas.NinjaModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/missoes")
public class MissoesController {

    private final MissoesService missoesService;

    public MissoesController(MissoesService missoesService) {
        this.missoesService = missoesService;
    }

    // Criar nova missao
    @PostMapping("/criar")
    @Operation(summary = "Cria uma nova missão", description = "Rota cria uma nova missão e insere no banco de dados" )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Missão criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro na criação da Missão")
    })
    public ResponseEntity<String> criarMissao(@RequestBody MissoesDTO missoes){
        MissoesDTO criarMissao = missoesService.criarMissao(missoes);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Missão Criada... (Nome): " + criarMissao.getNome() + " (Id): " + criarMissao.getId());
    }

    // Listar todas missoes
    @GetMapping("/listar")
    @Operation(summary = "Lista todas as missão", description = "Lista todas as missão presentes no banco de dados" )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de todas as missoes retornada com sucesso"),
            @ApiResponse(responseCode = "404", description = "O recurso solicitado não foi encontrado. No caso de 'listar', isso pode indicar um erro de infraestrutura ou que a coleção de missões não existe")
    })
    public ResponseEntity<List<MissoesDTO>> listarMissao(){
        List<MissoesDTO> missoes = missoesService.listarMissoes();
        return ResponseEntity.ok(missoes);
    }

    // Listar todas missoes por ID
    @GetMapping("/listar/{id}")
    @Operation(summary = "Lista missão por id", description = "Rota lista uma missão pelo seu id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Missão encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Missão não encontrado")
    })
    public ResponseEntity<?> listarMissaoPorId(
            @Parameter(description = "O usuário manda o id no caminho da requisição")
            @PathVariable Long id){
        MissoesDTO missoes = missoesService.listarMissoesPorId(id);
        if (missoes != null) {
            return ResponseEntity.ok(missoes);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body( "(Id): " + id + ", Missão não encontrada");
        }
    }

    // Alterar dados da missao
    @PutMapping("/alterar/{id}")
    @Operation(summary = "Altera a missão por id", description = "Rota altera uma missão pelo seu id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Missão alterado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Missão não encontrada, não foi possível realizar a alteração")
    })
    public ResponseEntity<?> alterarMissaoPorId(
            @Parameter(description = "O usuário manda o id no caminho da requisição")
            @PathVariable Long id,
            @Parameter(description = "Usuário manda os dados da missão a ser atualizada no corpo da requisição")
            @RequestBody MissoesDTO missaoAtualizada){
        MissoesDTO missoes = missoesService.atualizarMissao(id, missaoAtualizada);
        if (missoes != null) {
            return ResponseEntity.ok(missoes);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("(Id): " + id + ", Não econtrado");
        }

    }

    // Deletar missao
    @DeleteMapping("/deletar/{id}")
    @Operation(summary = "Deleta a missão por id", description = "Rota deleta uma missão pelo seu id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Missão deletada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Missão não encontrada, não foi possível realizar a deleção")
    })
    public ResponseEntity<String> deletarMissaoPorId(
            @Parameter(description = "O usuário manda o id no caminho da requisição")
            @PathVariable Long id){
        if (deletarMissaoPorId(id) != null) {
            missoesService.deletarMissaoPorId(id);
            return ResponseEntity.ok("Missão Deletada... (Id): " + id);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("(Id): " + id + ", Não encontrado");
        }
    }


}
