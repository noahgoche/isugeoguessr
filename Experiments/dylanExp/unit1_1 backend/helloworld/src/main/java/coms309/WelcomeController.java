package coms309;
import java.util.Random;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class WelcomeController {

    @GetMapping("/")
    public String welcome() {
        return "Hello and welcome to COMS 309";
    }

    @GetMapping("/numtest")
    public String displayNum() {
        String value = "";
        Random random = new Random(); 
        for (int i = 0; i < 100; i++) {
            int randomNumber = random.nextInt(100); 
            value += randomNumber + " "; 

        }

        return value;
    }
}
