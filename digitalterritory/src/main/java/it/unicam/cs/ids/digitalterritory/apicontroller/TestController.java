package it.unicam.cs.ids.digitalterritory.apicontroller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("tests")
public class TestController {
    
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public ResponseEntity<Object> test() {
        return ResponseEntity.ok().body("Test OK");
    }
}
