package pl.pjwstk.jaz.allezon.categories;


import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class EditCategoryRequest {

    private String name;
    private String newName;
    private String sectionName;

    public String getName() {
        return name;
    }

    public String getNewName() {
        return newName;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNewName(String newName) {
        this.newName = newName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }
}
