package nc.agilesoft.stage.ia.data.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Resultat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String reponse;

    @Column(columnDefinition = "TEXT")
    private String evaluation;

    @Column(name = "temps_ms", nullable = false)
    private Integer tempsMs;

    @Column(name = "executed_at")
    private LocalDateTime executedAt = LocalDateTime.now();

    @Column(columnDefinition = "TEXT", nullable = false)
    private String prompt;

    @Column(nullable = false)
    private String llm;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String question;
}
