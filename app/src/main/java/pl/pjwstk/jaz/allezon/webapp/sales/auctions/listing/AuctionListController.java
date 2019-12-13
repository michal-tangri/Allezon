package pl.pjwstk.jaz.allezon.webapp.sales.auctions.listing;

import pl.pjwstk.jaz.allezon.webapp.sales.auctions.entities.Auction;
import pl.pjwstk.jaz.allezon.webapp.sales.auctions.services.AuctionDisplayerService;
import pl.pjwstk.jaz.allezon.webapp.sales.photos.entities.Photo;
import pl.pjwstk.jaz.allezon.webapp.categorization.categories.entities.Category;
import pl.pjwstk.jaz.allezon.webapp.utilities.AllezonUtils;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.NoSuchElementException;

@Named
@RequestScoped
public class AuctionListController {

    @Inject
    private AuctionDisplayerService auctionDisplayerService;

    @Inject
    private AllezonUtils utils;

    private AuctionListRequest auctionListRequest;

    boolean auctionDoesNotExist = false;
    boolean wrongParameterInLink = false;
    boolean auctionIdNotSpecified = false;

    public void filter() {
    }

    public Photo getMiniatureByAuctionId(Long id) {
        List<Photo> photos = auctionDisplayerService.getAllPhotosByAuctionId(id);
        try {
            return photos.get(0);
        }
        catch (IndexOutOfBoundsException err0) {
            return null;
        }

    }

    public Auction getAuctionById() {
        if(utils.linkContains("auctionId")) {
            var auctionId = utils.getLongFromLink("auctionId");

            if(auctionId == null) {
                wrongParameterInLink = true;
                return null;
            }

            try {
                return auctionDisplayerService.getAuctionById(auctionId).orElseThrow();
            }
            catch (NoSuchElementException err0) {
                auctionDoesNotExist = true;
                return null;
            }
        }
        auctionIdNotSpecified =  true;
        return null;
    }

    public List<Category> getAllCategories() {
        return auctionDisplayerService.getAllCategories();
    }

    public List<Auction> getAllAuctionsByCategoryId(Long categoryId) {
        return auctionDisplayerService.getAllAuctionsByCategoryId(categoryId);
    }

    public List<Auction> getAllAuctionsByUsername() {
        return auctionDisplayerService.getAllAuctionsByUsername();
    }

    public List<Photo> getAllPhotosByAuctionId(Long id) {
        return auctionDisplayerService.getAllPhotosByAuctionId(id);
    }

    //Getters and setters
    public AuctionListRequest getAuctionListRequest() {
        if(auctionListRequest == null)
            auctionListRequest = new AuctionListRequest();
        return auctionListRequest;
    }

    public boolean isAuctionDoesNotExist() {
        return auctionDoesNotExist;
    }

    public boolean isWrongParameterInLink() {
        return wrongParameterInLink;
    }

    public boolean isAuctionIdNotSpecified() {
        return auctionIdNotSpecified;
    }
}
