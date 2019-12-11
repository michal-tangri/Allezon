package pl.pjwstk.jaz.allezon.auctions;

import pl.pjwstk.jaz.allezon.entities.auctions.Auction;
import pl.pjwstk.jaz.allezon.entities.sections.Category;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
@RequestScoped
public class AuctionListController {

    @Inject
    private AuctionRepository auctionRepository;

    private AuctionListRequest auctionListRequest;

    public void filter() {
    }

    public List<Category> getAllCategories() {
        return auctionRepository.findAllCategories();
    }

    public List<Auction> getAllAuctionsByCategory(String name) {
        return auctionRepository.findAllAuctionsByCategory(name);
    }

    //Getters and setters
    public AuctionListRequest getAuctionListRequest() {
        if(auctionListRequest == null)
            auctionListRequest = new AuctionListRequest();
        return auctionListRequest;
    }
}