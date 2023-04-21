package ch.ilv.ebanking;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SecurityRequirement(name = "bearerAuth")
@RestController
public class EbankingController {
    @RolesAllowed("admin")
    @GetMapping("/hello")
    public String hello() {
        return "hello world";
    }
}
