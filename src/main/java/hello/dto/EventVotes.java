package hello.dto;

import hello.entity.Choice;
import hello.entity.User;

import java.util.ArrayList;
import java.util.List;

public class EventVotes {

    public EventVotes() {
        this.users = new ArrayList<>();
        this.choices = new ArrayList<>();
        this.votedFor = new ArrayList<>();
    }

    // column headings, in order
    public List<User> users;

    // row headings, in order
    public List<ChoiceProjected> choices;

    // true at a column/row intersection means the user voted for that choice
    public List<boolean[]> votedFor;

}
