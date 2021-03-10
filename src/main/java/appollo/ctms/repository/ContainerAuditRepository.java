package appollo.ctms.repository;

import appollo.ctms.model.ContainerAuditEntity;
import appollo.ctms.model.ContainerAuditId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContainerAuditRepository extends JpaRepository<ContainerAuditEntity, ContainerAuditId> {

    List<ContainerAuditEntity> findAllByNumberOrderByUpdatedAt(String number);

    List<ContainerAuditEntity> findAllByBatchOrderByUpdatedAt(String batch);
}
