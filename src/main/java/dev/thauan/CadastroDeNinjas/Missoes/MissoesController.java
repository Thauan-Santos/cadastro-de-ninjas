package dev.thauan.CadastroDeNinjas.Missoes;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/missoes")
public class MissoesController {

    private MissoesService missoesService;

    public MissoesController(MissoesService missoesService) {
        this.missoesService = missoesService;
    }

    // Listar todas missoes
    @GetMapping("/listar")
    public List<MissoesModel> listarMissao(){
        return missoesService.listarMissoes();
    }

    @GetMapping("/listarId")
    public String listarMissaoPorId(){
        return "Missoes listadas";
    }

    // Criar nova missao
    @PostMapping("/criar")
    public String criarMissao(){
        return "Missao criada";
    }

    // Atualizar missao
    @PutMapping("/alterarId")
    public String alterarMissaoPorId(){
        return "Missao alterada";
    }

    // Deletar missao
    @DeleteMapping("/deletar")
    public String deletarMissao(){
        return "Missao deletada";
    }


}
