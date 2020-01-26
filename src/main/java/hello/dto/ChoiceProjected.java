package hello.dto;

import hello.entity.Choice;

public class ChoiceProjected {

    public Long id;

    public String title;

    public ChoiceProjected() {

    }

    public ChoiceProjected(Choice other) {
        this.id = other.getId();
        this.title = other.getTitle();
    }


}
