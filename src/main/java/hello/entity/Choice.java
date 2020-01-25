package hello.entity;

import javax.persistence.*;
import java.util.List;

/**
 * A Choice represents one of n options for an Event like "Mr Jonny's" or "Oct 3rd"
 */
@Entity
public class Choice {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column
    private String title;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    @OneToMany(mappedBy = "choice")
    private List<Vote> votes;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public List<Vote> getVotes() {
        return votes;
    }

    public Long getId() {
        return id;
    }

}
