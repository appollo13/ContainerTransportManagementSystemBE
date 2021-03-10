package appollo.ctms.model;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "containers_aud", indexes = {
        @Index(name = "c_short_index", columnList = "number"),
        @Index(name = "c_complete_index", columnList = "number, batch")
})
public class ContainerAuditEntity extends AbstractEntity {

    @JsonUnwrapped
    @EmbeddedId
    private ContainerAuditId containerAuditId;

    @Column(nullable = false, updatable = false)
    private Integer revtype;

    @Column(nullable = false, updatable = false)
    private String number;
    @Column(nullable = false, updatable = false)
    private String batch;

    @Column(name = "delivery_type", nullable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    private DeliveryType deliveryType;
    @Column(name = "gross_weight", nullable = false, updatable = false)
    private Integer grossWeight;

    @Column(name = "net_weight", updatable = false)
    private Integer netWeight;
    @Column(name = "net_weight_plus", updatable = false)
    private Integer netWeightPlus;

    @Column(name = "location_name", updatable = false)
    private String locationName;
    @Column(name = "composition_truck", updatable = false)
    private String compositionTruck;
    @Column(name = "composition_trailer", updatable = false)
    private String compositionTrailer;

    @Column(name = "heating_begin", updatable = false)
    private LocalDateTime heatingBegin;
    @Column(name = "heating_end", updatable = false)
    private LocalDateTime heatingEnd;

    @Column(name = "unloading_begin", updatable = false)
    private LocalDateTime unloadingBegin;
    @Column(name = "unloading_end", updatable = false)
    private LocalDateTime unloadingEnd;
}
