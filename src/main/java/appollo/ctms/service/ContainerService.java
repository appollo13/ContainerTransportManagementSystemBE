package appollo.ctms.service;

import appollo.ctms.model.*;
import appollo.ctms.repository.CompositionRepository;
import appollo.ctms.repository.ContainerRepository;
import appollo.ctms.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ContainerService {

    private final LocationRepository locationRepository;
    private final CompositionRepository compositionRepository;
    private final ContainerRepository containerRepository;

    @Autowired
    public ContainerService(LocationRepository locationRepository,
                            CompositionRepository compositionRepository,
                            ContainerRepository containerRepository) {
        this.locationRepository = locationRepository;
        this.compositionRepository = compositionRepository;
        this.containerRepository = containerRepository;
    }

    public List<ContainerEntity> list() {
        return containerRepository.findAll();
    }

    public ContainerEntity create(ContainerEntity container) {
        container.setId(null);
        //container.setNumber();
        //container.setBatch();
        //container.setDeliveryType();
        //container.setGrossWeight();
        container.setNetWeight(null);
        container.setNetWeightPlus(null);
        //container.setLocation();
        if (container.getLocation().getType() != LocationType.IncomingPort) {
            throw new RuntimeException("New container must be created at an IncomingPort");
        }
        container.setComposition(null);
        container.setHeatingBegin(null);
        container.setHeatingEnd(null);
        container.setUnloadingBegin(null);
        container.setUnloadingEnd(null);

        return containerRepository.save(container);
    }

    public ContainerEntity get(String id) {
        return containerRepository.getOne(id);
    }

    public ContainerEntity loadOnComposition(String id, CompositionId compositionId) {
        ContainerEntity oldContainer = containerRepository.getOne(id);
        CompositionEntity newComposition = compositionRepository.getOne(compositionId);
        return loadOnComposition(oldContainer, newComposition);
    }

    public ContainerEntity loadOnComposition(ContainerEntity oldContainer, CompositionEntity newComposition) {
        LocationEntity oldLocation = oldContainer.getLocation();
        if (oldLocation == null) {
            throw new RuntimeException("Current location is unknown!");
        }
        if (oldLocation.getType() == LocationType.OutgoingDepot) {
            throw new RuntimeException("Already processed!");
        }

        switch (oldLocation.getType()) {
            case HeatingPort -> {
                oldContainer.setHeatingEnd(LocalDateTime.now());
            }
            case Unloading, UnloadingPlus -> {
                throw new RuntimeException("Wrong method for unloading container content!");
            }
        }

        oldContainer.setComposition(newComposition);
        oldContainer.setLocation(null);

        containerRepository.save(oldContainer);
        return containerRepository.getOne(oldContainer.getId());
    }

    public ContainerEntity loadOnCompositionAfterUnloadingContent(String id, CompositionId compositionId, Integer netWeight) {
        ContainerEntity oldContainer = containerRepository.getOne(id);
        CompositionEntity newComposition = compositionRepository.getOne(compositionId);
        return loadOnCompositionAfterUnloadingContent(oldContainer, newComposition, netWeight);
    }

    public ContainerEntity loadOnCompositionAfterUnloadingContent(ContainerEntity oldContainer, CompositionEntity newComposition, Integer netWeight) {
        LocationEntity oldLocation = oldContainer.getLocation();
        if (oldLocation == null) {
            throw new RuntimeException("Current location is unknown!");
        }
        if (oldLocation.getType() == LocationType.OutgoingDepot) {
            throw new RuntimeException("Already processed!");
        }

        switch (oldLocation.getType()) {
            case Unloading -> {
                if (netWeight > oldContainer.getGrossWeight()) {
                    throw new RuntimeException("Net > Gross !");
                }
                oldContainer.setUnloadingEnd(LocalDateTime.now());
                oldContainer.setNetWeight(netWeight);
            }
            case UnloadingPlus -> {
                if (netWeight + oldContainer.getNetWeight() > oldContainer.getGrossWeight()) {
                    throw new RuntimeException("Net + NetPlus > Gross !");
                }
                oldContainer.setNetWeightPlus(netWeight);
            }
            default -> throw new RuntimeException("Wrong location for unloading container content!");
        }

        oldContainer.setComposition(newComposition);
        oldContainer.setLocation(null);

        containerRepository.save(oldContainer);
        return containerRepository.getOne(oldContainer.getId());
    }

    public ContainerEntity unloadAtLocation(String id, String locationName) {
        ContainerEntity oldContainer = containerRepository.getOne(id);
        LocationEntity newLocation = locationRepository.getOne(locationName);
        return unloadAtLocation(oldContainer, newLocation);
    }

    public ContainerEntity unloadAtLocation(ContainerEntity oldContainer, LocationEntity newLocation) {
        if (oldContainer.getComposition() == null) {
            throw new RuntimeException("Current composition is unknown!");
        }

        if (newLocation.getType() == LocationType.HeatingPort) {
            oldContainer.setHeatingBegin(LocalDateTime.now());
            oldContainer.setHeatingEnd(null);
        }
        if (newLocation.getType() == LocationType.Unloading) {
            oldContainer.setUnloadingBegin(LocalDateTime.now());
            oldContainer.setUnloadingEnd(null);
        }

        oldContainer.setLocation(newLocation);
        oldContainer.setComposition(null);
        containerRepository.save(oldContainer);
        return containerRepository.getOne(oldContainer.getId());
    }
}
