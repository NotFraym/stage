package nc.agilesoft.stage.ia.data.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.web.bind.annotation.*;

@RestController
public class ChatController {

    private final OpenAiChatModel chatModel;
    private final VectorStore vectorStore;

    public ChatController(OpenAiChatModel chatModel, VectorStore vectorStore) {
        this.chatModel = chatModel;
        this.vectorStore = vectorStore;
    }

    @PostMapping("/ask")
    public String chat(@RequestParam("question") String question,
                       @RequestParam("prompt") String promptTemplate) {

        String currentDate = java.time.LocalDate.now().toString();
        String finalPrompt = String.format(promptTemplate, currentDate);

        return ChatClient.builder(chatModel)
                .build()
                .prompt()
                .advisors(new QuestionAnswerAdvisor(vectorStore))
                .system(finalPrompt)
                .user(question)
                .call()
                .content();
    }
}

