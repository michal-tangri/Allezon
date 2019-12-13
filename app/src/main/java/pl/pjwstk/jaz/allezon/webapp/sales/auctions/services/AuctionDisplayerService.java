package pl.pjwstk.jaz.allezon.webapp.sales.auctions.services;

import pl.pjwstk.jaz.allezon.webapp.authorization.session.CurrentSession;
import pl.pjwstk.jaz.allezon.webapp.categorization.categories.entities.Category;
import pl.pjwstk.jaz.allezon.webapp.categorization.categories.repositories.CategoryRepository;
import pl.pjwstk.jaz.allezon.webapp.sales.auctions.entities.Auction;
import pl.pjwstk.jaz.allezon.webapp.sales.auctions.repositories.AuctionRepository;
import pl.pjwstk.jaz.allezon.webapp.sales.photos.entities.Photo;
import pl.pjwstk.jaz.allezon.webapp.sales.photos.repositories.PhotoRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class AuctionDisplayerService {

    @Inject
    private AuctionRepository auctionRepository;

    @Inject
    private PhotoRepository photoRepository;

    @Inject
    private CategoryRepository categoryRepository;

    @Inject
    private CurrentSession session;


    public Optional<Auction> getAuctionById(Long auctionId) {
        return auctionRepository.findAuctionById(auctionId);
    }

    public List<Auction> getAllAuctionsByCategoryId(Long categoryId) {
        return auctionRepository.findAllAuctionsByCategoryId(categoryId);
    }

    public List<Auction> getAllAuctionsByUsername() {
        return auctionRepository.findAllAuctionsByUsername(session.getUsername());
    }

    public List<Photo> getAllPhotosByAuctionId(Long auctionId) {
        return photoRepository.findAllPhotosByAuctionId(auctionId);
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAllCategories();
    }
}
