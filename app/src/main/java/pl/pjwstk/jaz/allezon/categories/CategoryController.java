package pl.pjwstk.jaz.allezon.categories;

import pl.pjwstk.jaz.allezon.entities.sections.Category;
import pl.pjwstk.jaz.allezon.entities.sections.Section;
import pl.pjwstk.jaz.allezon.sections.SectionRepository;
import pl.pjwstk.jaz.allezon.webapp.AllezonUtils;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.NoSuchElementException;

@Named
@RequestScoped
public class CategoryController {

    @Inject
    private CategoryRepository categoryRepository;

    @Inject
    private AllezonUtils utils;

    private CategoryRequest categoryRequest;

    private boolean categoryAlreadyExists = false;
    private boolean changingCategorySuccessful = false;
    private boolean categoryDoesNotExist = false;
    private boolean wrongParameterInLink = false;

    public void save() {
        Category category = categoryRequest.toCategory();
        category.setSection(categoryRepository.findSectionByName(categoryRequest.getSectionName()));
        try {
            categoryRepository.save(category);
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
                category = categoryRepository.findCategoryById(categoryId).orElseThrow();
            }
            catch (NoSuchElementException err0) {
                categoryDoesNotExist = true;
            }
            return new CategoryRequest(category);
        }
        return new CategoryRequest();
    }

    public List<Section> getAllSections() {
        return categoryRepository.findAllSections();
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
