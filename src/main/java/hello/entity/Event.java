package hello.entity;


import javax.persistence.*;
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

    @OneToMany(mappedBy = "event")
    private List<Choice> choices;

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


}
