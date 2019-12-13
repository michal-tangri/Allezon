package pl.pjwstk.jaz.allezon.webapp.categorization.categories.services;

import pl.pjwstk.jaz.allezon.webapp.categorization.categories.entities.Category;
import pl.pjwstk.jaz.allezon.webapp.categorization.categories.repositories.CategoryRepository;
import pl.pjwstk.jaz.allezon.webapp.categorization.sections.entities.Section;
import pl.pjwstk.jaz.allezon.webapp.categorization.sections.repositories.SectionRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class CategoryDisplayerService {

    @Inject
    private CategoryRepository categoryRepository;

    @Inject
    private SectionRepository sectionRepository;


    public List<Category> getAllCategoriesBySectionName(String sectionName) {
        return categoryRepository.findAllCategoriesBySectionName(sectionName);
    }

    public List<Section> getAllSections() {
        return sectionRepository.findAllSections();
    }
}
