package io.falcon.assignment.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Entity
@Table(name = "messages")
@Getter
@Setter
@NoArgsConstructor
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String content;
    private ZonedDateTime timestamp;

    public Message(String content, ZonedDateTime timestamp) {
        this.content = content;
        this.timestamp = timestamp;
    }
}
