package pl.pjwstk.jaz.allezon.categories;

import pl.pjwstk.jaz.allezon.entities.sections.Category;
import pl.pjwstk.jaz.allezon.entities.sections.Section;
import pl.pjwstk.jaz.allezon.repositories.CategoryRepository;
import pl.pjwstk.jaz.allezon.repositories.SectionRepository;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class CategoryController {

    @Inject
    AddCategoryRequest addRequest;

    @Inject
    EditCategoryRequest editRequest;

    @Inject
    CategoryRepository database;

    @Inject
    SectionRepository sectionDatabase;

    private boolean addingCategorySuccessful = false;

    private boolean editingCategorySuccessful = false;

    public void add() {
        String name = addRequest.getName();
        String sectionName = addRequest.getSectionName();
        if(!name.equals("")) {
            Section section = sectionDatabase.getSection(sectionName);
            Category category = new Category(name, section);
            database.addCategory(category);
            addRequest.setName(null);
            addingCategorySuccessful = true;
        }
    }

    public void edit() {
        String categoryName = editRequest.getName();
        String categoryNewName = editRequest.getNewName();

        if(categoryNewName.equals("")) {
            categoryNewName = categoryName;
        }

        Section section = sectionDatabase.getSection(editRequest.getSectionName());
        database.editCategory(categoryName, categoryNewName, section);
        editingCategorySuccessful = true;
    }

    public boolean isEditingCategorySuccessful() {
        return editingCategorySuccessful;
    }

    public boolean isAddingCategorySuccessful() {
        return addingCategorySuccessful;
    }
}
