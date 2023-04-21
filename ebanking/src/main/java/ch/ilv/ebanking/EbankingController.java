package ch.ilv.ebanking;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EbankingController {
    @GetMapping("/hello")
    public String hello() {
        return "hello world";
    }
}
