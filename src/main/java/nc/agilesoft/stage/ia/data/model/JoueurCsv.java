package nc.agilesoft.stage.ia.data.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.Map;

public class JoueurCsv {

    @JsonProperty("Club")
    private String club;

    @JsonProperty("Championnat")
    private String championnat;

    @JsonProperty("Joueur")
    private String joueur;

    @JsonProperty("Date de naissance")
    private String dateNaissance;

    @JsonProperty("Poste")
    private String poste;

    @JsonProperty("Equipe nationale")
    private String equipeNationale;

    @JsonProperty("Pied fort")
    private String piedFort;

    @JsonProperty("Valeur marchande")
    private String valeurMarchande;

    @JsonProperty("Statut contrat")
    private String statutContrat;

    @JsonProperty("Date fin de contrat")
    private String finContrat;

    @JsonProperty("Date début de contrat")
    private String debutContrat;

    @JsonProperty("Club précédent")
    private String clubPrecedent;


    public Map<String, Object> toMetadataMap() {
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("club", club);
        metadata.put("championnat", championnat);
        metadata.put("joueur", joueur);
        metadata.put("date_naissance", dateNaissance);
        metadata.put("poste", poste);
        metadata.put("equipe_nationale", equipeNationale);
        metadata.put("pied_fort", piedFort);
        metadata.put("valeur_marchande", valeurMarchande);
        metadata.put("statut_contrat", statutContrat);
        metadata.put("fin_contrat", finContrat);
        metadata.put("debut_contrat", debutContrat);
        metadata.put("club_precedent", clubPrecedent);
        return metadata;
    }

    public String toContentString() {
        return """
        %s, né le %s, joue au poste de %s pour le club %s, dans le championnat %s.
        Il est %s du pied, représente l’équipe nationale %s et sa valeur marchande est estimée à %s.
        Il est actuellement %s (contrat du %s au %s). Ancien club : %s.
        """.formatted(
                joueur != null ? joueur : "(Nom inconnu)",
                dateNaissance != null ? dateNaissance : "(inconnue)",
                poste != null ? poste : "(non précisé)",
                club != null ? club : "(club inconnu)",
                championnat != null ? championnat : "(championnat inconnu)",
                piedFort != null ? piedFort : "(pied inconnu)",
                equipeNationale != null ? equipeNationale : "(non précisée)",
                valeurMarchande != null ? valeurMarchande : "(non communiquée)",
                statutContrat != null ? statutContrat : "(statut inconnu)",
                debutContrat != null ? debutContrat : "(inconnu)",
                finContrat != null ? finContrat : "(inconnu)",
                clubPrecedent != null ? clubPrecedent : "(non renseigné)"
        );
    }
}
