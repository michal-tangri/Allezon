package pl.pjwstk.jaz.allezon.categories;

import pl.pjwstk.jaz.allezon.entities.sections.Section;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class AddCategoryRequest {

    private String name;
    private String sectionName;

    public String getName() {
        return name;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }
}
