package hello;

import hello.entity.Choice;
import hello.entity.Event;
import hello.entity.Vote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import hello.entity.User;

import java.util.*;

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
	public @ResponseBody String addNewUser (
			@RequestParam String name,
			@RequestParam String email) {
		User n = new User();
		n.setName(name);
		n.setEmail(email);
		userRepository.save(n);
		return "Saved";
	}

	@PutMapping(path="/event")
	public @ResponseBody String addEvent (
			@RequestParam String name) {
		Event e = new Event();
		e.setName(name);
		Event saved = eventRepository.save(e);
		return "Saved id "+saved.getId();
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
	public @ResponseBody String addVote (
			@RequestParam Long choiceId,
			@RequestParam Long userId,
			@RequestParam String comments) {
		Vote v = new Vote();
		v.setChoice(choiceRepository.findById(choiceId).get());
		v.setUser(userRepository.findById(userId).get());
		v.setComments(comments);
		Vote saved = voteRepository.save(v);
		return "Saved id "+saved.getId();
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
	public @ResponseBody String eventVotes(@RequestParam Long eventId) {
		Optional<Event> event = eventRepository.findById(eventId);
		// choice id indexed by position
		ArrayList<Long> choiceIds = new ArrayList<>();
		Map<Long, boolean[]> votes = new HashMap<>();
		if (event.isPresent()) {
			for (Choice c : event.get().getChoices()) {
				choiceIds.add(c.getId());
			}
			int i = 0;
			for (Choice c : event.get().getChoices()) {
				for (Vote v : c.getVotes()) {
					User u = v.getUser();
					if (!votes.containsKey(u.getId())) {
						votes.put(u.getId(), new boolean[choiceIds.size()]);
					}
					if (choiceIds.get(i).longValue() == c.getId().longValue()) {
						votes.get(u.getId())[i] = true;
					}
				}
				i++;
			}
		}
		StringBuffer sb = new StringBuffer();
		for (Map.Entry<Long, boolean[]> entry : votes.entrySet()) {
			sb.append(entry.getKey() + ": ");
			for (boolean b : entry.getValue()) {
				sb.append(b + ", ");
			}
			sb.append("\n");
		}
		return sb.toString();
	}


	@GetMapping(path="/all")
	public @ResponseBody Iterable<User> getAllUsers() {
		// This returns a JSON or XML with the users
		return userRepository.findAll();
	}
}
