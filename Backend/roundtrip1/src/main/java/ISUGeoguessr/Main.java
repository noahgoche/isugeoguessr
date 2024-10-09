package ISUGeoguessr;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import ISUGeoguessr.UserData.UserDataRepository;
import ISUGeoguessr.UserData.UserData;
import ISUGeoguessr.Location.Location;
import ISUGeoguessr.Location.LocationRepository;
import ISUGeoguessr.Stats.Stats;
import ISUGeoguessr.Stats.StatsRepository;


@SpringBootApplication
@EnableJpaRepositories
class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Bean
    CommandLineRunner initUser(UserDataRepository userDataRepository, LocationRepository locationRepository, StatsRepository statsRepository) {
        return args -> {
            UserData userData1 = new UserData("John", "john@somemail.com", "PWD");
            UserData userData2 = new UserData("Jane", "jane@somemail.com", "123");
            UserData userData3 = new UserData("Justin", "justin@somemail.com", "test");
            userDataRepository.save(userData1);
            userDataRepository.save(userData2);
            userDataRepository.save(userData3);

            Location location1 = new Location("Ames", "12,32,43");
            Location location2 = new Location("Des Moines", "34,12,12");
            Location location3 = new Location("Slater", "34,243,243");

            locationRepository.save(location1);
            locationRepository.save(location2);
            locationRepository.save(location3);

            Stats stats1 = new Stats("Easymode");
            statsRepository.save(stats1);
        };
    }
}