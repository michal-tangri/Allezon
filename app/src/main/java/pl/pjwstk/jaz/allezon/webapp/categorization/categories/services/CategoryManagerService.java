package pl.pjwstk.jaz.allezon.webapp.categorization.categories.services;

import pl.pjwstk.jaz.allezon.webapp.categorization.categories.entities.Category;
import pl.pjwstk.jaz.allezon.webapp.categorization.categories.repositories.CategoryRepository;
import pl.pjwstk.jaz.allezon.webapp.categorization.sections.entities.Section;
import pl.pjwstk.jaz.allezon.webapp.categorization.sections.repositories.SectionRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class CategoryManagerService {

    @Inject
    private CategoryRepository categoryRepository;

    @Inject
    private SectionRepository sectionRepository;

    public void saveCategory(Category category) throws Exception {
        categoryRepository.save(category);
    }

    public Optional<Category> getCategoryById(Long categoryId) {
        return categoryRepository.findCategoryById(categoryId);
    }

    public Section getSectionByName(String sectionName) {
        return sectionRepository.findSectionByName(sectionName);
    }

    public List<Section> getAllSections() {
        return sectionRepository.findAllSections();
    }
}
