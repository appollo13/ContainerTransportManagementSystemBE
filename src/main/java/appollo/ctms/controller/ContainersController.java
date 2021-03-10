package appollo.ctms.controller;

import appollo.ctms.model.CompositionId;
import appollo.ctms.model.ContainerEntity;
import appollo.ctms.service.ContainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@Valid
@RestController
@RequestMapping("/containers")
public class ContainersController {

    private final ContainerService containerService;

    @Autowired
    public ContainersController(ContainerService containerService) {
        this.containerService = containerService;
    }

    @GetMapping
    public List<ContainerEntity> list() {
        return containerService.list();
    }

    @PostMapping
    public ContainerEntity create(
            @Valid @RequestBody ContainerEntity container
    ) {
        return containerService.create(container);
    }

    @GetMapping("/{id}")
    public ContainerEntity get(
            @PathVariable("id") String id
    ) {
        return containerService.get(id);
    }

    @PatchMapping("/{id}/composition/{truck}/{trailer}")
    public ContainerEntity loadOnComposition(
            @PathVariable("id") String id,
            @PathVariable("truck") String truck,
            @PathVariable("trailer") String trailer
    ) {
        return containerService.loadOnComposition(id, new CompositionId(truck, trailer));
    }

    @PatchMapping("/{id}/composition/{truck}/{trailer}?net-weight={netWeight}")
    public ContainerEntity loadOnCompositionAfterUnloadingContent(
            @PathVariable("id") String id,
            @PathVariable("truck") String truck,
            @PathVariable("trailer") String trailer,
            @Valid @NotNull @Min(1) @RequestParam("net-weight") Integer netWeight
    ) {
        return containerService.loadOnCompositionAfterUnloadingContent(id, new CompositionId(truck, trailer), netWeight);
    }

    @PatchMapping("/{id}/location/{location}")
    public ContainerEntity unloadAtLocation(
            @PathVariable("id") String id,
            @PathVariable("location") String location
    ) {
        return containerService.unloadAtLocation(id, location);
    }
}