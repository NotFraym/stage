package nc.agilesoft.stage.ia.data.controller;

import lombok.RequiredArgsConstructor;
import nc.agilesoft.stage.ia.data.service.InjectionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class InjectionController {

    private final InjectionService injectionService;

    @PostMapping("/api/inject")
    public ResponseEntity<String> inject(@RequestParam String llm, @RequestParam String promptKey) {
        System.out.println(">> Appel reçu avec LLM: " + llm + ", Prompt: " + promptKey);

        try {
            injectionService.injectData(llm, promptKey);
            return ResponseEntity.ok("Succès");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Erreur : " + e.getMessage());
        }
    }

    @DeleteMapping("/api/clear")
    public ResponseEntity<String> clearDatabase() {
        try {
            injectionService.clearData();
            //Réponse claire en texte brut avec bon encodage
            return ResponseEntity
                    .ok()
                    .header("Content-Type", "text/plain; charset=UTF-8")
                    .body("Base vidée avec succès.");
        } catch (Exception e) {
            return ResponseEntity
                    .status(500)
                    .header("Content-Type", "text/plain; charset=UTF-8")
                    .body("Erreur serveur : " + e.getMessage());
        }
    }






}
