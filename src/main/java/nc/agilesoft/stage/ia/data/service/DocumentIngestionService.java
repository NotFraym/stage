package nc.agilesoft.stage.ia.data.service;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import lombok.extern.slf4j.Slf4j;
import nc.agilesoft.stage.ia.data.model.JoueurCsv;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@Profile("!test")
public class DocumentIngestionService implements CommandLineRunner {

    @Value("classpath:/documents/joueurs.csv")
    private Resource resource;

    private final VectorStore vectorStore;

    public DocumentIngestionService(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    @Override
    public void run(String... args) throws Exception {
        boolean alreadyIngested = !Objects.requireNonNull(vectorStore.similaritySearch("test")).isEmpty();
        if (alreadyIngested) {
            log.info("Des documents sont déjà présents dans le vector store.");
            return;
        }

        List<Document> documents = readDocumentsFromCsv();
        vectorStore.accept(documents);
        log.info("📥 Ingestion CSV terminée. %d documents ingérés.".formatted(documents.size()));
    }

    private List<Document> readDocumentsFromCsv() throws Exception {
        InputStream inputStream = resource.getInputStream();
        CsvMapper csvMapper = new CsvMapper();
        CsvSchema schema = CsvSchema.emptySchema().withHeader(); // lit la 1re ligne comme en-tête
        MappingIterator<JoueurCsv> it = csvMapper.readerFor(JoueurCsv.class).with(schema).readValues(inputStream);
        return it.readAll().stream()
                .map(joueur -> new Document(joueur.toContentString(), joueur.toMetadataMap()))
                .toList();
    }
}
