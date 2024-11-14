package ISUGeoguessr.Chat;

import java.util.Date;

import ISUGeoguessr.UserData.UserData;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "messages")
@Data
@Getter @Setter @NoArgsConstructor
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String userName;

    @Lob
    private String content;

    @Column
    private String chatroom;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "sent on")
    private Date sent = new Date();

    @ManyToOne
    @JoinColumn(name = "userdata_id")
    @JsonIgnore
    private UserData userData;

    public Message(String userName, String content, String chatroom) {
        this.userName = userName;
        this.content = content;
        this.chatroom = chatroom;

    }

}