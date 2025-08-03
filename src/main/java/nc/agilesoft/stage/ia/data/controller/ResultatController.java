package nc.agilesoft.stage.ia.data.controller;

import lombok.RequiredArgsConstructor;
import nc.agilesoft.stage.ia.data.model.Resultat;
import nc.agilesoft.stage.ia.data.repository.ResultatRepository;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/resultats")
@RequiredArgsConstructor
public class ResultatController {

    private final ResultatRepository resultatRepository;

    @GetMapping
    public List<Resultat> getAll() {
        return resultatRepository.findAll();
    }

    @PostMapping
    public Resultat save(@RequestBody Resultat resultat) {
        if (resultat.getExecutedAt() == null) {
            resultat.setExecutedAt(LocalDateTime.now());
        }
        return resultatRepository.save(resultat);
    }
}

