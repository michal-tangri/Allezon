package pl.pjwstk.jaz.allezon.auctions;

import pl.pjwstk.jaz.allezon.entities.auctions.Auction;
import pl.pjwstk.jaz.allezon.entities.auctions.Photo;
import pl.pjwstk.jaz.allezon.entities.sections.Category;
import pl.pjwstk.jaz.allezon.webapp.AllezonUtils;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.NoSuchElementException;

@Named
@RequestScoped
public class AuctionListController {

    @Inject
    private AuctionRepository auctionRepository;

    @Inject
    private AllezonUtils utils;

    private AuctionListRequest auctionListRequest;

    boolean auctionDoesNotExist = false;
    boolean wrongParameterInLink = false;
    boolean auctionIdNotSpecified = false;

    public void filter() {
    }

    public List<Category> getAllCategories() {
        return auctionRepository.findAllCategories();
    }

    public List<Auction> getAllAuctionsByCategory(String name) {
        return auctionRepository.findAllAuctionsByCategory(name);
    }

    public List<Auction> getAllAuctionsByUsername(String username) {
        return auctionRepository.findAllAuctionsByUsername(username);
    }

    public List<Photo> getAllPhotosByAuctionId(Long id) {
        return auctionRepository.findAllPhotosByAuctionId(id);
    }

    public Photo getMiniatureByAuctionId(Long id) {
        List<Photo> photos = auctionRepository.findAllPhotosByAuctionId(id);
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
                return auctionRepository.findAuctionById(auctionId).orElseThrow();
            }
            catch (NoSuchElementException err0) {
                auctionDoesNotExist = true;
                return null;
            }
        }
        auctionIdNotSpecified =  true;
        return null;
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