package pl.pjwstk.jaz.allezon.auctions;

import pl.pjwstk.jaz.allezon.entities.auctions.Auction;
import pl.pjwstk.jaz.allezon.entities.auctions.AuctionParameter;

import javax.servlet.http.Part;
import java.util.ArrayList;
import java.util.List;

public class AuctionRequest {

    private Long id;
    private String title;
    private String description;
    private String price;
    private String categoryName;

    private List<String> parametersValues = new ArrayList<>();
    private List<String> parametersNames = new ArrayList<>();

    private Part[] photos = new Part[4];

    public AuctionRequest() {
        for(int i = 1; i <= 10; i++) {
            parametersValues.add("");
            parametersNames.add("");
        }

    }

    public AuctionRequest(Auction auction) {
        this.id = auction.getId();
        this.title = auction.getTitle();
        this.description = auction.getDescription();
        this.price = auction.getPrice().toString();
        this.categoryName = auction.getCategory() == null ? null : auction.getCategory().getName();

        for(AuctionParameter p : auction.getParameters()) {
            parametersNames.add(p.getParameter().getName());
            parametersValues.add(p.getValue());
        }
    }

    public Auction toAuction() {
        Double convertedPrice = Double.parseDouble(price.replace(',', '.'));
        return new Auction(id, title, description, convertedPrice);
    }

    //Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Part[] getPhotos() {
        return photos;
    }

    public void setPhotos(Part[] photos) {
        this.photos = photos;
    }

    public List<String> getParametersValues() {
        return parametersValues;
    }

    public void setParametersValues(List<String> parametersValues) {
        this.parametersValues = parametersValues;
    }

    public List<String> getParametersNames() {
        return parametersNames;
    }

    public void setParametersNames(List<String> parametersNames) {
        this.parametersNames = parametersNames;
    }
}

