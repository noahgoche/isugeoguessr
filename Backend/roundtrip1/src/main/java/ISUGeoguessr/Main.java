package ISUGeoguessr;

import java.util.Date;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


import ISUGeoguessr.User.User;
import ISUGeoguessr.User.UserRepository;
import ISUGeoguessr.Location.Location;
import ISUGeoguessr.Location.LocationRepository;


@SpringBootApplication
@EnableJpaRepositories
class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Bean
    CommandLineRunner initUser(UserRepository userRepository, LocationRepository locationRepository) {
        return args -> {
            User user1 = new User("John", "john@somemail.com", "PWD");
            User user2 = new User("Jane", "jane@somemail.com", "123");
            User user3 = new User("Justin", "justin@somemail.com", "test");
            userRepository.save(user1);
            userRepository.save(user2);
            userRepository.save(user3);

            Location location1 = new Location("Ames", "12,32,43");
            Location location2 = new Location("Des Moines", "34,12,12");
            Location location3 = new Location("Slater", "34,243,243");

            locationRepository.save(location1);
            locationRepository.save(location2);
            locationRepository.save(location3);
        };
    }
}