package appollo.ctms.controller;

import appollo.ctms.model.LocationEntity;
import appollo.ctms.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/locations")
public class LocationsController {

    private final LocationRepository locationRepository;

    @Autowired
    public LocationsController(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @GetMapping
    public List<LocationEntity> list() {
        return locationRepository.findAll();
    }

    @PostMapping
    public LocationEntity create(@RequestBody LocationEntity location) {
        return locationRepository.save(location);
    }

    @GetMapping("/{name}")
    public LocationEntity get(@PathVariable("name") String name) {
        return locationRepository.getOne(name);
    }
}
