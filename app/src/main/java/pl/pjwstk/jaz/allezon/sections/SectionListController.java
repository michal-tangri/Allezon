package pl.pjwstk.jaz.allezon.sections;

import pl.pjwstk.jaz.allezon.entities.sections.Section;

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
        return sectionRepository.findAll();
    }

}
