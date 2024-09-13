package coms309;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
class WelcomeController {

    private String name;
    private static final String message = "Hello and welcome to COMS 309, %s";

    @GetMapping("/")
    public String welcome() {
        return "Hello and welcome to COMS 309";
    }

    @GetMapping("/{name}")
    public String welcome(@PathVariable String name) {
        this.name = name;
        return String.format(message, name);
    }

    @GetMapping("/{nameFirst}/{nameLast}")
    public String hello(@PathVariable String nameFirst,@PathVariable String nameLast)
    {
            return "Hello " + nameFirst + " " + nameLast + ".";
    }

    @GetMapping("/class/{classISU}")
    public String classWelcome(@PathVariable String classISU)
    {
        return "Hello and welcome to " + classISU + " " + name;
    }

    @GetMapping("/add/{val1}/{val2}")
    public String add(@PathVariable String val1, @PathVariable String val2)
    {
        Integer value1 = Integer.parseInt(val1);
        Integer value2 = Integer.parseInt(val2);
        int sum = value1 + value2;
        return "Val1 + Val2 = " + sum;
    }

}
