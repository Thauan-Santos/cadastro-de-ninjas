package dev.thauan.CadastroDeNinjas.Missoes;

import dev.thauan.CadastroDeNinjas.Ninjas.NinjaModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/missoes")
public class MissoesController {

    private MissoesService missoesService;

    public MissoesController(MissoesService missoesService) {
        this.missoesService = missoesService;
    }

    // Criar nova missao
    @PostMapping("/criar")
    public MissoesModel criarMissao(@RequestBody MissoesModel missoes){
        return missoesService.criarMissao(missoes);
    }

    // Listar todas missoes
    @GetMapping("/listar")
    public List<MissoesModel> listarMissao(){
        return missoesService.listarMissoes();
    }

    // Listar todas missoes por ID
    @GetMapping("/listar/{id}")
    public MissoesModel listarMissaoPorId(@PathVariable Long id){
        return missoesService.listarMissoesPorId(id);
    }

    // Alterar dados da missao
    @PutMapping("/alterar/{id}")
    public MissoesModel alterarMissaoPorId(@PathVariable Long id, @RequestBody MissoesModel missaoAtualizada){
        return missoesService.atualizarMissao(id, missaoAtualizada);
    }

    // Deletar missao
    @DeleteMapping("/deletar/{id}")
    public void deletarMissaoPorId(@PathVariable Long id){
        missoesService.deletarMissaoPorId(id);
    }


}
