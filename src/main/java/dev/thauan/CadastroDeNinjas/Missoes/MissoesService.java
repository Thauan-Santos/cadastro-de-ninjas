package dev.thauan.CadastroDeNinjas.Missoes;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MissoesService {

    private final MissoesRepository missoesRepository;
    private final MissoesMapper missoesMapper;

    public MissoesService(MissoesRepository missoesRepository, MissoesMapper missoesMapper) {
        this.missoesRepository = missoesRepository;
        this.missoesMapper = missoesMapper;
    }

    // Criar missao
    public MissoesDTO criarMissao(MissoesDTO missoesDTO) {
        MissoesModel missoes = missoesMapper.map(missoesDTO);
        missoes = missoesRepository.save(missoes);
        return missoesMapper.map(missoes);
    }

    // Listar todas missoes
    public List<MissoesDTO> listarMissoes() {
        List<MissoesModel> missoes = missoesRepository.findAll();
        return missoes.stream()
                .map(missoesMapper::map)
                .collect(Collectors.toList());
    }

    // Listar todas missoes por ID
    public MissoesDTO listarMissoesPorId(Long id) {
        Optional<MissoesModel> missoesPorId = missoesRepository.findById(id);
        return missoesPorId.map(missoesMapper::map).orElse(null);
    }

    // Deletar missao
    public void deletarMissaoPorId (Long id){
        missoesRepository.deleteById(id);
    }

    // Atualizar missao
    public MissoesDTO atualizarMissao(Long id, MissoesDTO missoesDTO) {
        Optional<MissoesModel> missoesExistente = missoesRepository.findById(id);
        if (missoesExistente.isPresent()) {
            MissoesModel missoesAtualizada = missoesMapper.map(missoesDTO);
            missoesAtualizada.setId(id);
            MissoesModel missaoSalva = missoesRepository.save(missoesAtualizada);
            return missoesMapper.map(missaoSalva);
        }
        return null;
    }
}
