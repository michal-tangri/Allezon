package pl.pjwstk.jaz.allezon.webapp.categorization.sections.listing;

import pl.pjwstk.jaz.allezon.webapp.categorization.sections.repositories.SectionRepository;
import pl.pjwstk.jaz.allezon.webapp.categorization.sections.entities.Section;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
@RequestScoped
public class SectionListController {

    @Inject
    private SectionRepository sectionRepository;

    public List<Section> getAllSections() {
        return sectionRepository.findAllSections();
    }

}
