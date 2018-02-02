package ru.agavshin.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.agavshin.entity.Demo;
import ru.agavshin.service.DemoService;

@RestController
@RequestMapping("/demo")
public class DemoResource {

    @Autowired
    private DemoService demoService;

    @GetMapping
    public ResponseEntity<Demo> sample() {
        return ResponseEntity.ok(demoService.sample());
    }

    @PostMapping("/send")
    public ResponseEntity<String> send(@RequestBody  Demo demo) {
        demoService.send(demo);
        return ResponseEntity.ok("SUCCESS");
    }
}
