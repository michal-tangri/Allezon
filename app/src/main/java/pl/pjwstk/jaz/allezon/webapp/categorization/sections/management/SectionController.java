package pl.pjwstk.jaz.allezon.webapp.categorization.sections.management;

import pl.pjwstk.jaz.allezon.webapp.categorization.sections.repositories.SectionRepository;
import pl.pjwstk.jaz.allezon.webapp.categorization.sections.entities.Section;
import pl.pjwstk.jaz.allezon.webapp.utilities.AllezonUtils;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.NoSuchElementException;

@Named
@RequestScoped
public class SectionController {

    @Inject
    private SectionRepository sectionRepository;

    @Inject
    private AllezonUtils utils;

    private SectionRequest sectionRequest;

    private boolean sectionAlreadyExists = false;
    private boolean sectionDoesNotExist = false;
    private boolean wrongParameterInLink = false;
    private boolean changingSectionsSuccessful = false;

    public void save() {
        Section section = sectionRequest.toSection();
        try {
            sectionRepository.save(section);
            changingSectionsSuccessful = true;
        }
        catch (Exception err0) {
            sectionAlreadyExists = true;
        }
    }

    private SectionRequest createSectionRequest() {
        if(utils.linkContains("sectionId")) {
            var sectionId = utils.getLongFromLink("sectionId");

            if(sectionId == null) {
                wrongParameterInLink = true;
                return new SectionRequest();
            }

            var section = new Section();
            try {
                section = sectionRepository.findSectionById(sectionId).orElseThrow();
            }
            catch (NoSuchElementException err0) {
                sectionDoesNotExist = true;
            }
            return new SectionRequest(section);
        }
        return new SectionRequest();
    }

    //Getters and setters
    public SectionRequest getSectionRequest() {
        if(sectionRequest == null)
            sectionRequest = createSectionRequest();
        return sectionRequest;
    }

    public boolean isChangingSectionsSuccessful() {
        return changingSectionsSuccessful;
    }

    public boolean isWrongParameterInLink() {
        return wrongParameterInLink;
    }

    public boolean isSectionAlreadyExists() {
        return sectionAlreadyExists;
    }

    public boolean isSectionDoesNotExist() {
        return sectionDoesNotExist;
    }
}