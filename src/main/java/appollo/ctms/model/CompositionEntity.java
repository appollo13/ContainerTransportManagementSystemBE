package appollo.ctms.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@IdClass(CompositionId.class)
@Table(name = "compositions")
@Audited
public class CompositionEntity extends AbstractEntity {

    @Id
    private String truck;

    @Id
    private String trailer;
}
