package ISUGeoguessr.Leaderboard;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@Configuration
public class LeaderboardWebSocketConfig {
    @Bean
    public ServerEndpointExporter serverEndpointExporter2() {
        return new ServerEndpointExporter();
    }

}
