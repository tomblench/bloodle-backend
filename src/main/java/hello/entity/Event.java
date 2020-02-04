package hello.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import hello.Util;

import javax.persistence.*;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

/**
 * An event like "Meal in January"
 */
@Entity
public class Event {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "event")
    private List<Choice> choices;

    // user must input this secret correctly to gain admin permissions; namely finishing event
    @Column
    private String secret;

    // public "key" for finding event (don't use id because it is sequential and can be guessed)
    @Column
    private String eventKey;

    @Column
    private boolean votingFinished;


    public Event() {
        this.secret = Util.randomBase64(8);
        this.eventKey = Util.randomBase64(8);
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public List<Choice> getChoices() {
        return choices;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSecret() {
        return secret;
    }

    public String getEventKey() {
        return eventKey;
    }

    public boolean isVotingFinished() {
        return votingFinished;
    }

    public void setVotingFinished(boolean votingFinished) {
        this.votingFinished = votingFinished;
    }
}
