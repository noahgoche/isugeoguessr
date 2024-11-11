package ISUGeoguessr.Chat;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

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

    public Message(String userName, String content, String chatroom) {
        this.userName = userName;
        this.content = content;
        this.chatroom = chatroom;
    }

}