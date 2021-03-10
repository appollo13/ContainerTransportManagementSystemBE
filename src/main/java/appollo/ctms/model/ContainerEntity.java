package appollo.ctms.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "containers",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"number", "batch"})}
)
@Audited
public class ContainerEntity extends AbstractEntity {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    @NotBlank
    @Column(unique = true, nullable = false, updatable = false)
    private String number;
    @NotBlank
    @Column(nullable = false, updatable = false)
    private String batch;

    @NotNull
    @Column(nullable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    private DeliveryType deliveryType;
    @NotNull
    @Min(1)
    @Column(nullable = false, updatable = false)
    private Integer grossWeight;

    @OneToOne
    private LocationEntity location;
    @OneToOne
    private CompositionEntity composition;

    @Column
    private LocalDateTime heatingBegin;
    @Column
    private LocalDateTime heatingEnd;

    @Column
    private LocalDateTime unloadingBegin;
    @Column
    private LocalDateTime unloadingEnd;

    @Column
    private Integer netWeight;
    @Column
    private Integer netWeightPlus;
}
