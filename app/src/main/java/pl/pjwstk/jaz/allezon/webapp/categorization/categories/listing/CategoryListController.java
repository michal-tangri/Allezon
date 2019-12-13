package pl.pjwstk.jaz.allezon.webapp.categorization.categories.listing;

import pl.pjwstk.jaz.allezon.webapp.categorization.categories.entities.Category;
import pl.pjwstk.jaz.allezon.webapp.categorization.categories.services.CategoryDisplayerService;
import pl.pjwstk.jaz.allezon.webapp.categorization.sections.entities.Section;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
@RequestScoped
public class CategoryListController {

    @Inject
    private CategoryDisplayerService categoryDisplayerService;

    private CategoryListRequest categoryListRequest;

    public void filter() {
    }

    public List<Category> getAllCategoriesBySectionName(String sectionName) {
        return categoryDisplayerService.getAllCategoriesBySectionName(sectionName);
    }

    public List<Section> getAllSections() {
        return categoryDisplayerService.getAllSections();
    }

    //Getters and setters
    public CategoryListRequest getCategoryListRequest() {
        if(categoryListRequest == null)
            categoryListRequest = new CategoryListRequest();
        return categoryListRequest;
    }
}
