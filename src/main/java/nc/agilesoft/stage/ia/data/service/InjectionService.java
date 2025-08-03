package nc.agilesoft.stage.ia.data.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.chat.client.ChatClient;
import nc.agilesoft.stage.ia.data.model.Resultat;
import nc.agilesoft.stage.ia.data.repository.ResultatRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;

import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class InjectionService {

    private final ResultatRepository repository;
    private final OpenAiChatModel chatModel;
    private final ObjectMapper mapper = new ObjectMapper();
    private final VectorStore vectorStore;

    public void injectData(String llm, String promptKey) throws Exception {
        // 1. Charger les prompts
        InputStream promptStream = getClass().getResourceAsStream("/static/donnees/prompt.json");
        List<Map<String, String>> prompts = mapper.readValue(promptStream, new TypeReference<>() {});
        String promptTemplate = prompts.stream()
                .filter(p -> promptKey.equals(p.get("code")))
                .findFirst()
                .map(p -> p.get("texte"))
                .orElseThrow(() -> new RuntimeException("Prompt non trouvé pour la clé : " + promptKey));

        // 2. Injecter la date dans le prompt
        String currentDate = java.time.LocalDate.now().toString();
        String prompt = String.format(promptTemplate, currentDate);

        // 3. Charger les questions
        InputStream questionStream = getClass().getResourceAsStream("/static/donnees/question.json");
        List<String> questions = mapper.readValue(questionStream, new TypeReference<>() {});

        // 4. Pour chaque question, générer une réponse avec le RAG
        for (String question : questions) {
            long start = System.currentTimeMillis();

            String reponse = ChatClient.builder(chatModel)
                    .build()
                    .prompt()
                    .advisors(new QuestionAnswerAdvisor(vectorStore))
                    .system(prompt)
                    .user(question)
                    .call()
                    .content();

            long duration = System.currentTimeMillis() - start;

            Resultat resultat = Resultat.builder()
                    .llm(llm)
                    .prompt(prompt)
                    .question(question)
                    .reponse(reponse)
                    .tempsMs((int) duration)
                    .executedAt(LocalDateTime.now())
                    .build();

            repository.save(resultat);
        }
    }

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void clearData() {
        repository.deleteAll();
        entityManager.createNativeQuery("ALTER SEQUENCE resultat_id_seq RESTART WITH 1").executeUpdate();
    }

}
