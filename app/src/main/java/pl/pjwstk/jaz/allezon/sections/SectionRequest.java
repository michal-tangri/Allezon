package pl.pjwstk.jaz.allezon.sections;

import pl.pjwstk.jaz.allezon.entities.sections.Section;

public class SectionRequest {

    private Long id;
    private String name;

    public SectionRequest() {
    }

    public SectionRequest(Section section) {
        this.id = section.getId();
        this.name = section.getName();
    }

    public Section toSection() {
        return new Section(id, name);
    }

    //Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
