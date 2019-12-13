package pl.pjwstk.jaz.allezon.categories;

import pl.pjwstk.jaz.allezon.entities.sections.Category;
import pl.pjwstk.jaz.allezon.entities.sections.Section;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
@RequestScoped
public class CategoryListController {

    @Inject
    private CategoryRepository categoryRepository;

    private CategoryListRequest categoryListRequest;

    public void filter() {
    }

    public List<Section> getAllSections() {
        return categoryRepository.findAllSections();
    }

    public List<Category> getAllCategoriesBySection(String name) {
        return categoryRepository.findAllBySection(name);
    }

    //Getters and setters
    public CategoryListRequest getCategoryListRequest() {
        if(categoryListRequest == null)
            categoryListRequest = new CategoryListRequest();
        return categoryListRequest;
    }
}
