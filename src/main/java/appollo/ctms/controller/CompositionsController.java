package appollo.ctms.controller;

import appollo.ctms.model.CompositionEntity;
import appollo.ctms.model.CompositionId;
import appollo.ctms.repository.CompositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/compositions")
public class CompositionsController {

    private final CompositionRepository compositionRepository;

    @Autowired
    public CompositionsController(CompositionRepository compositionRepository) {
        this.compositionRepository = compositionRepository;
    }

    @GetMapping
    public List<CompositionEntity> list() {
        return compositionRepository.findAll();
    }

    @PostMapping
    public CompositionEntity create(@RequestBody CompositionEntity composition) {
        return compositionRepository.save(composition);
    }

    @GetMapping("/{truck}/{trailer}")
    public CompositionEntity get(@PathVariable("truck") String truck, @PathVariable("trailer") String trailer) {
        return compositionRepository.getOne(new CompositionId(truck, trailer));
    }
}
