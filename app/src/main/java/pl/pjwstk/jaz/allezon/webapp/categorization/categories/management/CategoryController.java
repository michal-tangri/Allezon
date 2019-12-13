package pl.pjwstk.jaz.allezon.webapp.categorization.categories.management;

import pl.pjwstk.jaz.allezon.webapp.categorization.categories.entities.Category;
import pl.pjwstk.jaz.allezon.webapp.categorization.categories.services.CategoryManagerService;
import pl.pjwstk.jaz.allezon.webapp.categorization.sections.entities.Section;
import pl.pjwstk.jaz.allezon.webapp.utilities.AllezonUtils;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.NoSuchElementException;

@Named
@RequestScoped
public class CategoryController {

    @Inject
    private CategoryManagerService categoryManagerService;

    @Inject
    private AllezonUtils utils;

    private CategoryRequest categoryRequest;

    private boolean categoryAlreadyExists = false;
    private boolean changingCategorySuccessful = false;
    private boolean categoryDoesNotExist = false;
    private boolean wrongParameterInLink = false;

    public void save() {
        Category category = categoryRequest.toCategory();
        category.setSection(categoryManagerService.getSectionByName(categoryRequest.getSectionName()));
        try {
            categoryManagerService.saveCategory(category);
            changingCategorySuccessful = true;
        }
        catch (Exception err0) {
            categoryAlreadyExists = true;
        }
    }

    private CategoryRequest createCategoryRequest() {
        if(utils.linkContains("categoryId")) {
            var categoryId = utils.getLongFromLink("categoryId");

            if(categoryId == null) {
                wrongParameterInLink = true;
                return new CategoryRequest();
            }

            var category = new Category();
            try {
                category = categoryManagerService.getCategoryById(categoryId).orElseThrow();
            }
            catch (NoSuchElementException err0) {
                categoryDoesNotExist = true;
            }
            return new CategoryRequest(category);
        }
        return new CategoryRequest();
    }

    public List<Section> getAllSections() {
        return categoryManagerService.getAllSections();
    }

    //Getters and setters
    public CategoryRequest getCategoryRequest() {
        if(categoryRequest == null)
            categoryRequest = createCategoryRequest();
        return categoryRequest;
    }

    public boolean isCategoryAlreadyExists() {
        return categoryAlreadyExists;
    }

    public boolean isChangingCategorySuccessful() {
        return changingCategorySuccessful;
    }

    public boolean isCategoryDoesNotExist() {
        return categoryDoesNotExist;
    }

    public boolean isWrongParameterInLink() {
        return wrongParameterInLink;
    }
}
