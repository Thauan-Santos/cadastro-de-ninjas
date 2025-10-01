package dev.thauan.CadastroDeNinjas.Ninjas;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ninjas")
public class NinjaController {

    private final NinjaService ninjaService;

    public NinjaController(NinjaService ninjaService) {
        this.ninjaService = ninjaService;
    }

    @GetMapping("/boasVindas")
    @Operation(summary = "Mensagem de boas vindas", description = "Essa rota da uma mensagem de boas vindas para quem acessa ela")
    public String boasVindas() {
        return "Essa é a minha primeira msg nessa rota";
    }

    // Adicionar Ninja (CREATE)
    @PostMapping("/criar")
    @Operation(summary = "Cria um novo ninja", description = "Rota cria um novo ninja e insere no bando de dados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Ninja criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro na criação do ninja")
    })
    public ResponseEntity<String> criarNinja(@RequestBody NinjaDTO ninja) {
        NinjaDTO novoNinja = ninjaService.criarNinja(ninja);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Ninja Criado... (Nome): " + novoNinja.getNome() + " (Id): " + novoNinja.getId());
    }

    // Mostrar todos os ninjas (READ)
    @GetMapping("/listar")
    @Operation(summary = "Lista todos os ninjas", description = "Rota lista todos ninja presentes no banco de dados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de todos os ninjas retornada com sucesso"),
            @ApiResponse(responseCode = "404", description = "O recurso solicitado não foi encontrado. No caso de 'listar', isso pode indicar um erro de infraestrutura ou que a coleção de ninjas não existe")
    })
    public ResponseEntity<List<NinjaDTO>> listarNinjas() {
        List<NinjaDTO> ninjas = ninjaService.listarNinjas();
        return ResponseEntity.ok(ninjas);
    }

    // Mostrar ninja por ID (READ)
    @GetMapping("/listar/{id}")
    @Operation(summary = "Lista ninja por id", description = "Rota lista um ninja pelo seu id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ninja encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Ninja não encontrado")
    })
    public ResponseEntity<?> listarNinjasPorId(
            @Parameter(description = "O usuário manda o id no caminho da requisição")
            @PathVariable Long id){
        NinjaDTO ninja = ninjaService.listarNinjasPorId(id);
        if (ninja != null) {
             return ResponseEntity.ok(ninja);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("(Id): " + id + ", Ninja não encontrado");
        }
    }

    // Alterar dados dos ninjas (UPDATE)
    @PutMapping("/alterar/{id}")
    @Operation(summary = "Altera o ninja por id", description = "Rota altera um ninja pelo seu id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ninja alterado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Ninja não encontrado, não foi possível realizar a alteração")
    })
    public ResponseEntity<?> alterarNinjaPorId(
            @Parameter(description = "O usuário manda o id no caminho da requisição")
            @PathVariable Long id,
            @Parameter(description = "Usuário manda os dados do ninja a ser atualizado no corpo da requisição")
            @RequestBody NinjaDTO ninjaAtualizado){
        NinjaDTO ninja = ninjaService.atualizarNinja(id, ninjaAtualizado);
        if (ninja != null) {
            return ResponseEntity.ok(ninja);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("(Id): " + id + ", Ninja não encontrado");
        }
    }
    // Deletar ninja (DELETE)
    @DeleteMapping("/deletar/{id}")
    @Operation(summary = "Deleta o ninja por id", description = "Rota deleta um ninja pelo seu id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ninja deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Ninja não encontrado, não foi possível realizar a deleção")
    })
    public ResponseEntity<String> deletarNinjaPorId(
            @Parameter(description = "O usuário manda o id no caminho da requisição")
            @PathVariable Long id){
        if (ninjaService.listarNinjasPorId(id) != null) {
            ninjaService.deletarNinjaPorId(id);
            return ResponseEntity.ok("Ninja Deletado... (id): " + id);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("(Id):" + id + ", Não encontrado");
        }
    }

}
