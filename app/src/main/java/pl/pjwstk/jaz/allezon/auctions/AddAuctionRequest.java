package pl.pjwstk.jaz.allezon.auctions;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.servlet.http.Part;
import java.util.ArrayList;
import java.util.List;

@Named
@RequestScoped
public class AddAuctionRequest {

    private String title;
    private String description;
    private String price;
    private String categoryName;
    private Part miniature;
    private Part photo1;
    private Part photo2;
    private Part photo3;


    public Part getMiniature() {
        return miniature;
    }

    public void setMiniature(Part miniature) {
        this.miniature = miniature;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Part getPhoto1() {
        return photo1;
    }

    public void setPhoto1(Part photo1) {
        this.photo1 = photo1;
    }

    public Part getPhoto2() {
        return photo2;
    }

    public void setPhoto2(Part photo2) {
        this.photo2 = photo2;
    }

    public Part getPhoto3() {
        return photo3;
    }

    public void setPhoto3(Part photo3) {
        this.photo3 = photo3;
    }
}
