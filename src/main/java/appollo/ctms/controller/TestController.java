package appollo.ctms.controller;

import appollo.ctms.model.*;
import appollo.ctms.repository.CompositionRepository;
import appollo.ctms.repository.LocationRepository;
import appollo.ctms.service.ContainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    private final LocationRepository locationRepository;
    private final CompositionRepository compositionRepository;
    private final ContainerService containerService;

    @Autowired
    public TestController(LocationRepository locationRepository,
                          CompositionRepository compositionRepository,
                          ContainerService containerService) {
        this.locationRepository = locationRepository;
        this.compositionRepository = compositionRepository;
        this.containerService = containerService;
    }

    @PostMapping("/init")
    public void init() {
        LocationEntity port = locationRepository.save(new LocationEntity("port", LocationType.IncomingPort));
        LocationEntity openStorage = locationRepository.save(new LocationEntity("openStorage", LocationType.StorageDepot));
        LocationEntity unloading = locationRepository.save(new LocationEntity("unloading", LocationType.Unloading));
        LocationEntity outgoingDepot = locationRepository.save(new LocationEntity("outgoingDepot", LocationType.OutgoingDepot));

        CompositionEntity firstComp = compositionRepository.save(new CompositionEntity("CB3090AH", "C8296MX"));

        ContainerEntity container1 = new ContainerEntity();
        container1.setNumber("c-n-1");
        container1.setBatch("b-A");
        container1.setDeliveryType(DeliveryType.Train);
        container1.setGrossWeight(10000);
        container1.setLocation(port);
        container1.setComposition(firstComp);
        container1 = containerService.create(container1);

        container1 = containerService.loadOnComposition(container1, firstComp);
        container1 = containerService.unloadAtLocation(container1, openStorage);
        container1 = containerService.loadOnComposition(container1, firstComp);
        container1 = containerService.unloadAtLocation(container1, unloading);
        container1 = containerService.loadOnCompositionAfterUnloadingContent(container1, firstComp, 7500);

        ContainerEntity container2 = new ContainerEntity();
        container2.setNumber("c-n-2");
        container2.setBatch("b-A");
        container2.setDeliveryType(DeliveryType.Train);
        container2.setGrossWeight(9900);
        container2.setLocation(port);
        container2.setComposition(firstComp);
        container2 = containerService.create(container2);
    }
}
