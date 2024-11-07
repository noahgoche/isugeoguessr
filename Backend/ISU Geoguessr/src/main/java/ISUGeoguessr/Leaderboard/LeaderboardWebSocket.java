package ISUGeoguessr.Leaderboard;

import ISUGeoguessr.Stats.*;
import ISUGeoguessr.UserData.*;

import java.io.IOException;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import ISUGeoguessr.UserData.UserDataRepository;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;

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

    @Autowired
    UserDataRepository userDataRepository;

    private final Logger logger = LoggerFactory.getLogger(LeaderboardWebSocket.class);

    List<Stats> statList = statsRepository.findAll();
    //List<UserData> userList = userDataRepository.findAll();

    List<Stats> top50;

    @OnOpen
    public void onOpen(Session session) throws IOException
    {
        logger.info("Opened leaderboard");
     //   Stats stats = statsRepository.findById(4);
     //   session.getBasicRemote().sendText(stats.getUsername());
   //       session.getBasicRemote().sendText(statList.size() + " ");
          session.getBasicRemote().sendText("test");


    }
    //   for(i = 0; i < 50; i++){
    //      find largest score and corresponding user name and add to top50 list in order
//            session.getBasicRemote().sendText(username and score)//not actual syntax
//
//      }

    @OnClose
    public void onClose(Session session) throws IOException {
        logger.info("Entered into Close");

    }
}
