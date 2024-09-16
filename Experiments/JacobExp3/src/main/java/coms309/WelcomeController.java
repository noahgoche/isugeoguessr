package coms309;

import java.util.Random;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.Random;

@RestController
class WelcomeController {

    @GetMapping("/randomnumbers")
    public String genRandom() {
        String value = "";
        Random random = new Random(); 
        for (int i = 0; i < 100000; i++) {
            int randomNumber = random.nextInt(1000000); 
            value += randomNumber + " "; 
        }
        return value;
    }

    @GetMapping("/randomnumbers/{target}")
    public String genRandom(@PathVariable int target) {
        if (target > 99999 || target < 0) {
            return "Please choose a number from 0 to 99,999";
        }

        String value = "";
        int count = 0;
        Random random = new Random(); 
        while (true) {
            int randomNumber = random.nextInt(100000); 
            value += "   " + randomNumber + "   "; 
            count++;
            System.out.println(count + "  -  " + randomNumber);

            if (randomNumber == target) {
                value += "  <------    It took " + count + " tries to find your number!!";
                break;
            }
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
