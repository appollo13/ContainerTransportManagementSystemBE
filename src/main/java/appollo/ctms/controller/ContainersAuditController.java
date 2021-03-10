package appollo.ctms.controller;

import appollo.ctms.model.ContainerAuditEntity;
import appollo.ctms.repository.ContainerAuditRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/history")
public class ContainersAuditController {

    private final ContainerAuditRepository containerAuditRepository;

    @Autowired
    public ContainersAuditController(ContainerAuditRepository containerAuditRepository) {
        this.containerAuditRepository = containerAuditRepository;
    }

    @GetMapping
    public List<ContainerAuditEntity> list() {
        return containerAuditRepository.findAll();
    }

    @GetMapping(params = {"number"})
    public List<ContainerAuditEntity> listByNumber(@RequestParam(name = "number") String number) {
        return containerAuditRepository.findAllByNumberOrderByUpdatedAt(number);
    }

    @GetMapping(params = {"batch"})
    public List<ContainerAuditEntity> listByBatch(@RequestParam(name = "batch") String batch) {
        return containerAuditRepository.findAllByBatchOrderByUpdatedAt(batch);
    }
}
