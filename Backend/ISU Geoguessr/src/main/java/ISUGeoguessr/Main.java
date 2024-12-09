package ISUGeoguessr;

import java.util.Date;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.context.annotation.ComponentScan;


import ISUGeoguessr.UserData.*;
import ISUGeoguessr.Location.*;
import ISUGeoguessr.Stats.*;
import ISUGeoguessr.Chat.*;
import ISUGeoguessr.Leaderboard.*;


@SpringBootApplication
@EnableJpaRepositories
@ComponentScan(basePackages = {"ISUGeoguessr"})
class Main {
    public static void main(String[] args) {
      //  System.setProperty("hibernate.enable_lazy_load_no_trans", "true");
        SpringApplication.run(Main.class, args);
    }

//  Test data
//    @Bean
//    CommandLineRunner initUser(UserDataRepository userDataRepository, LocationRepository locationRepository, StatsRepository statsRepository) {
//        return args -> {
//            UserData userData1 = new UserData("John", "john@somemail.com", "PWD");
//            UserData userData2 = new UserData("Jane", "jane@somemail.com", "123");
//            UserData userData3 = new UserData("Justin", "justin@somemail.com", "test");
//            userDataRepository.save(userData1);
//            userDataRepository.save(userData2);
//            userDataRepository.save(userData3);
//
//            Location location1 = new Location("Ames", 100, 20120, "TestImage");
//            Location location2 = new Location("Des Moines", 100, 20120, "TestImage");
//            Location location3 = new Location("Slater", 100, 20120, "TestImage");
//
//            locationRepository.save(location1);
//            locationRepository.save(location2);
//            locationRepository.save(location3);
//
//            Stats stats1 = new Stats("Easymode");
//            statsRepository.save(stats1);
//        };
//    }
}