package dev.thauan.CadastroDeNinjas.Missoes;

import dev.thauan.CadastroDeNinjas.Ninjas.NinjaModel;
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
    public ResponseEntity<String> criarMissao(@RequestBody MissoesDTO missoes){
        MissoesDTO criarMissao = missoesService.criarMissao(missoes);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Missão Criada... (Nome): " + criarMissao.getNome() + " (Id): " + criarMissao.getId());
    }

    // Listar todas missoes
    @GetMapping("/listar")
    public ResponseEntity<List<MissoesDTO>> listarMissao(){
        List<MissoesDTO> missoes = missoesService.listarMissoes();
        return ResponseEntity.ok(missoes);
    }

    // Listar todas missoes por ID
    @GetMapping("/listar/{id}")
    public ResponseEntity<?> listarMissaoPorId(@PathVariable Long id){
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
    public ResponseEntity<?> alterarMissaoPorId(@PathVariable Long id, @RequestBody MissoesDTO missaoAtualizada){
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
    public ResponseEntity<String> deletarMissaoPorId(@PathVariable Long id){
        if (deletarMissaoPorId(id) != null) {
            missoesService.deletarMissaoPorId(id);
            return ResponseEntity.ok("Missão Deletada... (Id): " + id);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("(Id): " + id + ", Não encontrado");
        }
    }


}
