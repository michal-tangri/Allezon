package pl.pjwstk.jaz.allezon.categories;

import pl.pjwstk.jaz.allezon.entities.sections.Category;

public class CategoryRequest {

    private Long id;
    private String name;
    private String sectionName;

    public CategoryRequest() {
    }

    public CategoryRequest(Category category) {
        this.id = category.getId();
        this.name = category.getName();
        this.sectionName = category.getSection() == null ? null : category.getSection().getName();
    }

    public Category toCategory() {
        return new Category(id, name);
    }

    //Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }
}
