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

    List<Stats> allStats = new ArrayList<Stats>();
    List<Stats> top50 = new ArrayList<Stats>();
    List<Stats> prevTop50 = new ArrayList<Stats>();

    private final Logger logger = LoggerFactory.getLogger(LeaderboardWebSocket.class);

    boolean listComp = false;

    Stats stats = new Stats();

    @OnOpen
    public void onOpen(Session session) throws IOException
    {
        logger.info("Opened leaderboard");

        this.session = session;

        if(statsRepository == null) {
            session.getBasicRemote().sendText("Stats Repository is empty");
    }
        //allStats holds all the stats objects in statsRepository
        allStats = statsRepository.findAll();

        //sort the list in order of highest total score to lowest
        allStats.sort(new Stats());

        //create a list holding a set amount for the leader board or all stats objects if the count is less than the max leaderboard size
        if (statsRepository.count() < 50)
            top50 = allStats;
        else
            top50 = allStats.subList(0, 50);

        //displays top players on open
        for (Stats stats : top50) {
            if (stats != null) {
                session.getBasicRemote().sendText(stats.getUsername() + " - " + stats.getTotalScore());
            }
        }

        //sets the prevTop50 = top50 for checking if any stats change or are added
        prevTop50 = top50;

        //while session is open the leaderboard will update
        while (this.session.isOpen()) {

            //check the statsRepository to see if anything has been added or changed
            //allStats holds all the stats objects in statsRepository
            allStats = statsRepository.findAll();

            //sort the list in order of highest total score to lowest
            allStats.sort(new Stats());

            //create a list holding a set amount for the leader board or all stats objects if the count is less than the max leaderboard size
            if (statsRepository.count() < 50)
                top50 = allStats;
            else
                top50 = allStats.subList(0, 50);

            //compare the previous stats data with newly received data and if any stats have been added or updated set listComp true
            //if the size of the current and previous top players match then compare each individual object otherwise set listComp true
            if(top50.size() == prevTop50.size()) {
                int count = 0;
                while(!listComp && count < top50.size())
                {
                    //if top50 doesn't have the exact same objects as previous top 50, listComp is set to true and breaks the loop
                    listComp = stats.compare(top50.get(count), prevTop50.get(count)) != 0;
                    count++;
                }
            }
            else
                listComp = true;

            //if top50 doesn't equal the previous top 50 then the list changes and updates the leaderboard
            if(listComp) {
                for (Stats stats : top50) {
                    if (stats != null) {
                        session.getBasicRemote().sendText(stats.getUsername() + " - " + stats.getTotalScore());
                    }
                }
                prevTop50 = top50;
                listComp = false;
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
