package nc.agilesoft.stage.ia.data.repository;

import nc.agilesoft.stage.ia.data.model.Resultat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResultatRepository extends JpaRepository<Resultat, Long> {
}
