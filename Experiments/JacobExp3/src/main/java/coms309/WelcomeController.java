package coms309;

import java.util.Random;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.Random;

@RestController
class WelcomeController {

    @GetMapping("/randomnumbers")
    public String arm() {
        String value = "";
        Random random = new Random(); 
        for (int i = 0; i < 100000; i++) {
            int randomNumber = random.nextInt(1000000); 
            value += randomNumber + "\n"; 
        }
        return value;
    }

    @GetMapping("/")
    public String welcome() {
        return "Hello and welcome to COMS 309";
    }

    @GetMapping("/{name}")
    public String welcome(@PathVariable String name) {
        return "Hello and welcome to COMS 309: " + name;
    }

}
