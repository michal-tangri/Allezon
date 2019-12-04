package pl.pjwstk.jaz.allezon.sections;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class EditSectionRequest {

    private String name;

    public String getNewName() {
        return newName;
    }

    public void setNewName(String newName) {
        this.newName = newName;
    }

    private String newName;

    public String getName() {
            return name;
        }

    public void setName(String name) {
            this.name = name;
        }
}
