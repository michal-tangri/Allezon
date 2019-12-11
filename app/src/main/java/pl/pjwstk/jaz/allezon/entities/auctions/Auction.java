package pl.pjwstk.jaz.allezon.entities.auctions;

import pl.pjwstk.jaz.allezon.auth.ProfileEntity;
import pl.pjwstk.jaz.allezon.entities.sections.Category;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "auction")
public class Auction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private Double price;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "auction_owner")
    private ProfileEntity profile;

    @OneToMany(
            mappedBy = "auction",
            cascade = {CascadeType.MERGE, CascadeType.REMOVE},
            orphanRemoval = true,
            fetch = FetchType.EAGER)
    private List<Photo> photos;

    @OneToMany(
            mappedBy = "auction",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE},
            orphanRemoval = true)
    private List<AuctionParameter> parameters;

    public Auction() {
    }

    public Auction(Long id, String title, String description, Double price) {
        this.title = title;
        this.description = description;
        this.price = price;
    }

    public String getMiniaturePath() {
        Photo miniature = photos.get(0);
        if(miniature != null)
            return miniature.getFilePath();
        return null;
    }

    //Getters and setters
    public Long getId() {
        return id;
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    public ProfileEntity getProfile() {
        return profile;
    }

    public void setProfile(ProfileEntity profile) {
        this.profile = profile;
    }

    public List<AuctionParameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<AuctionParameter> parameters) {
        this.parameters = parameters;
    }
}