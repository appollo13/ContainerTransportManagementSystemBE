package appollo.ctms.repository;

import appollo.ctms.model.CompositionEntity;
import appollo.ctms.model.CompositionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompositionRepository extends JpaRepository<CompositionEntity, CompositionId> {
}
