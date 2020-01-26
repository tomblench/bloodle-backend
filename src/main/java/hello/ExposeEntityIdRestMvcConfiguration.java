package hello;

import hello.entity.Choice;
import hello.entity.Event;
import hello.entity.User;
import hello.entity.Vote;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.stereotype.Component;

@Component
public class ExposeEntityIdRestMvcConfiguration implements RepositoryRestConfigurer {

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        config.exposeIdsFor(User.class);
        config.exposeIdsFor(Event.class);
        config.exposeIdsFor(Vote.class);
        config.exposeIdsFor(Choice.class);
    }
}