package appollo.ctms.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

import javax.persistence.*;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "locations")
@Audited
public class LocationEntity extends AbstractEntity {

    @Id
    private String name;

    @Column(nullable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    private LocationType type;
}
