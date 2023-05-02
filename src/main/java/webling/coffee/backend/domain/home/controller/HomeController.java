package webling.coffee.backend.domain.home.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
public class HomeController {

    @GetMapping("/nginxHealthCheck")
    @ResponseBody
    public String nginxHealthCheckController () {
        return "healthy";
    }
}
