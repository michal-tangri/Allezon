package pl.pjwstk.jaz.allezon.sections;

import pl.pjwstk.jaz.allezon.repositories.SectionRepository;
import pl.pjwstk.jaz.allezon.entities.sections.Section;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class SectionController {

    @Inject
    AddSectionRequest addRequest;

    @Inject
    EditSectionRequest editRequest;

    @Inject
    SectionRepository database;

    private boolean addingSectionSuccessful = false;

    private boolean editingSectionSuccessful = false;

    public void add() {
        String sectionName = addRequest.getName();
        if (!sectionName.equals("")) {
            Section newSection = new Section(sectionName);
            database.addSection(newSection);
            addRequest.setName(null);
            addingSectionSuccessful = true;
        }
    }

    public void edit() {
        String sectionNewName = editRequest.getNewName();
        if (!sectionNewName.equals("")) {
            database.editSection(editRequest.getName(), editRequest.getNewName());
            editRequest.setNewName(null);
            editingSectionSuccessful = true;
        }
    }

    public boolean isAddingSectionSuccessful() {
        return addingSectionSuccessful;
    }

    public boolean isEditingSectionSuccessful() {
        return editingSectionSuccessful;
    }
}
