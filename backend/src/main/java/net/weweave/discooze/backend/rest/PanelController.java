package net.weweave.discooze.backend.rest;

import net.weweave.discooze.backend.domain.Panel;
import net.weweave.discooze.backend.service.PanelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PanelController {
    @Autowired
    private PanelRepository repository;

    @RequestMapping(value = "/panelxx/list", method = RequestMethod.GET)
    public ResponseEntity<Iterable<Panel>> list() {
        Iterable<Panel> list = getRepository().findAll();
        return ResponseEntity.ok(list);
    }

    @RequestMapping(value = "/panelxx/save", method = RequestMethod.PUT)
    public ResponseEntity<String> save() {
        Panel panel = new Panel();
        panel.setTitle("Test 1");
        panel.setUrl("/testtest");
        panel = getRepository().save(panel);
        return ResponseEntity.ok(panel.getId().toString());
    }

    public PanelRepository getRepository() {
        return repository;
    }

    public void setRepository(PanelRepository repository) {
        this.repository = repository;
    }
}
