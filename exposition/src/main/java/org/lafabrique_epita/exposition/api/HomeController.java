package org.lafabrique_epita.exposition.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    @GetMapping("/")
    public ResponseEntity home() {
        return ResponseEntity.ok("ok");
    }

}
