package ISUGeoguessr.Leaderboard;

import ISUGeoguessr.Stats.*;
import ISUGeoguessr.UserData.*;

import java.io.IOException;
import java.util.*;

import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;

import org.aspectj.bridge.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;


@Controller      // this is needed for this to be an endpoint to springboot
@ServerEndpoint(value = "/Leaderboard")  // this is Websocket url
public class LeaderboardWebSocket {

    private static StatsRepository statsRepository;

    @Autowired
    public void setStatsRepository(StatsRepository statsRepo){
        statsRepository = statsRepo;
    }

    private Session session;

    private static List<Stats> statList = new ArrayList<Stats>();

    List<Stats> top50;
    List<Stats> prevTop50;
    private final Logger logger = LoggerFactory.getLogger(LeaderboardWebSocket.class);


    @OnOpen
    public void onOpen(Session session) throws IOException
    {
        logger.info("Opened leaderboard");

        this.session = session;

        if(statsRepository == null)
            session.getBasicRemote().sendText("Stats Repository is empty");


        while (session.isOpen()) {
            statList = statsRepository.findAll();
            statList.sort(new Stats());

            if (statList.size() < 50)
                top50 = statList;
            else
                top50 = statList.subList(0, 50);

            if(!top50.equals(prevTop50)) {
                for (Stats stats : top50) {
                    if (stats != null) {
                        session.getBasicRemote().sendText(stats.getUsername() + " - " + stats.getTotalScore());
                    }
                }
                prevTop50 = top50;
            }
        }

    }

    @OnClose
    public void onClose(Session session) throws IOException {
        logger.info("Entered into Close");

    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        // Do error handling here
        logger.info("Entered into Error");
        throwable.printStackTrace();
    }

}
