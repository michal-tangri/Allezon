package pl.pjwstk.jaz.allezon.categories;

public class CategoryListRequest {

    private String sectionName;

    public CategoryListRequest() {
    }

    public CategoryListRequest(String sectionName) {
        this.sectionName = sectionName;
    }

    //Getters and setters
    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }
}
