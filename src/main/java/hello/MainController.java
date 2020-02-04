package hello;

import hello.dto.EventVotes;
import hello.entity.Choice;
import hello.entity.Event;
import hello.entity.Vote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import hello.entity.User;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

@Controller    // This means that this class is a Controller
@RequestMapping(path="/demo") // This means URL's start with /demo (after Application path)
public class MainController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ChoiceRepository choiceRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private VoteRepository voteRepository;


    @PutMapping(path="/user")
    public @ResponseBody User addNewUser (
            @RequestParam String name,
            @RequestParam String email) {
        User n = new User();
        n.setName(name);
        n.setEmail(email);
        return userRepository.save(n);
    }

    @PutMapping(path="/event")
    public @ResponseBody String addEvent (
            @RequestParam String name) {
        Event e = new Event();
        e.setName(name);
        Event saved = eventRepository.save(e);
        return "Saved id "+saved.getId();
    }

    @GetMapping(path="/event")
    public @ResponseBody Event getEvent (
            @RequestParam String eventKey) {
        try {
            return eventRepository.findByEventKey(eventKey).get(0);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(path="/event/end")
    public @ResponseBody Event endEvent (
            @RequestParam String eventKey,
            @RequestParam String secret) {
        Event e = eventRepository.findByEventKey(eventKey).get(0);
        if (e.getSecret().equals(secret)) {
            e.setVotingFinished(true);
            return eventRepository.save(e);
        }
        throw new ResponseStatusException(HttpStatus.FORBIDDEN);
    }

    @PutMapping(path="/choice")
    public @ResponseBody String addChoice (
            @RequestParam String title,
            @RequestParam Long eventId) {
        Choice c = new Choice();
        c.setTitle(title);
        c.setEvent(eventRepository.findById(eventId).get());
        Choice saved = choiceRepository.save(c);
        return "Saved id "+saved.getId();
    }

    @PutMapping(path="/vote")
    public @ResponseBody Object addVote (
            @RequestParam Long choiceId,
            @RequestParam Long userId,
            @RequestParam String comments) {
        Vote v = new Vote();
        v.setChoice(choiceRepository.findById(choiceId).get());
        v.setUser(userRepository.findById(userId).get());
        v.setComments(comments);
        Vote saved = voteRepository.save(v);
        return Map.of("id", saved.getId());
    }

    @GetMapping(path="/list")
    public @ResponseBody String listVotes() {
        StringBuffer sb = new StringBuffer();
        for (Event e : eventRepository.findAll()) {
            sb.append("Event "+e.getName()+"\n");
            for (Choice c : e.getChoices()) {
                sb.append("  Choice "+c.getTitle()+"\n");
                for (Vote v : c.getVotes()) {
                    sb.append("    Voted for by "+v.getUser().getName()+", "+v.getComments()+"\n");
                }
            }
        }
        return sb.toString();
    }

    // generate a matrix of users x choices with true/false entries
    @GetMapping(path="/eventVotes")
    public @ResponseBody EventVotes eventVotes(@RequestParam Long eventId) {
        Optional<Event> event = eventRepository.findById(eventId);
        if (!event.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        // choice id indexed by position
        ArrayList<Long> choiceIds = new ArrayList<>();
        // vote boolean list hashed by user id
        Map<Long, boolean[]> votes = new HashMap<>();
        if (event.isPresent()) {
            for (Choice c : event.get().getChoices()) {
                choiceIds.add(c.getId());
            }
            for (int i=0; i<choiceIds.size(); i++) {
                Choice c = choiceRepository.findById(choiceIds.get(i)).get();
                for (Vote v : c.getVotes()) {
                    User u = v.getUser();
                    if (!votes.containsKey(u.getId())) {
                        votes.put(u.getId(), new boolean[choiceIds.size()]);
                    }
                    if (choiceIds.get(i).longValue() == c.getId().longValue()) {
                        votes.get(u.getId())[i] = true;
                    }
                }
            }
        }
        EventVotes eventVotes = new EventVotes();

        for (int i=0; i<choiceIds.size(); i++) {
            Choice choice = choiceRepository.findById(choiceIds.get(i)).get();
            eventVotes.choices.add(choice);
        }

        // sort by user id so newest users' votes are at the start
        for (Map.Entry<Long, boolean[]> entry : votes.entrySet().stream().sorted((e1,e2) -> (int)(e2.getKey() - e1.getKey())).collect(Collectors.toList())) {
            eventVotes.users.add(userRepository.findById(entry.getKey()).get());
            eventVotes.votedFor.add(entry.getValue());
        }

        eventVotes.eventName = eventRepository.findById(eventId).get().getName();

        return eventVotes;
    }


    @GetMapping(path="/all")
    public @ResponseBody Iterable<User> getAllUsers() {
        // This returns a JSON or XML with the users
        return userRepository.findAll();
    }
}
