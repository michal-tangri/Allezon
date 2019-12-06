package pl.pjwstk.jaz.allezon.entities.sections;

import javax.persistence.*;

@Entity
@Table(name = "section")
public class Section {

    @Id
    @SequenceGenerator(name="sectionGenerator", sequenceName="section_id_seq", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sectionGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    public Section() {
    }

    public Section(String name) {
        this.name = name;
    }

    public Section(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Section(Section section) {
        this.id = section.getId();
        this.name = section.getName();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
