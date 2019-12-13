package pl.pjwstk.jaz.allezon.webapp.sales.auctions.services;

import pl.pjwstk.jaz.allezon.webapp.authorization.entities.ProfileEntity;
import pl.pjwstk.jaz.allezon.webapp.authorization.repositories.ProfileRepository;
import pl.pjwstk.jaz.allezon.webapp.categorization.categories.entities.Category;
import pl.pjwstk.jaz.allezon.webapp.categorization.categories.repositories.CategoryRepository;
import pl.pjwstk.jaz.allezon.webapp.sales.auctions.entities.Auction;
import pl.pjwstk.jaz.allezon.webapp.sales.auctions.repositories.AuctionRepository;
import pl.pjwstk.jaz.allezon.webapp.sales.parameters.entities.AuctionParameter;
import pl.pjwstk.jaz.allezon.webapp.sales.parameters.entities.Parameter;
import pl.pjwstk.jaz.allezon.webapp.sales.parameters.repositories.ParameterRepository;
import pl.pjwstk.jaz.allezon.webapp.sales.photos.entities.Photo;
import pl.pjwstk.jaz.allezon.webapp.sales.photos.repositories.PhotoRepository;
import pl.pjwstk.jaz.allezon.webapp.authorization.session.CurrentSession;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class AuctionManagerService {

    @Inject
    private AuctionRepository auctionRepository;

    @Inject
    private ParameterRepository parameterRepository;

    @Inject
    private PhotoRepository photoRepository;

    @Inject
    private CategoryRepository categoryRepository;

    @Inject
    private ProfileRepository profileRepository;

    @Inject
    private CurrentSession session;

    public void saveAuction(Auction auction) {
        auctionRepository.save(auction);
    }

    public Optional<Auction> getAuctionById(Long auctionId) {
        return auctionRepository.findAuctionById(auctionId);
    }

    public AuctionParameter getAuctionParameterById(Long parameterId, Long auctionId) {
        return parameterRepository.findAuctionParameterById(parameterId, auctionId);
    }

    public Parameter getParameterByName(String parameterName) {
        return parameterRepository.findParameterByName(parameterName);
    }

    public List<Parameter> getAllParameters() {
        return parameterRepository.findAllParameters();
    }

    public List<Photo> getAllPhotosByAuctionId(Long auctionId) {
        return photoRepository.findAllPhotosByAuctionId(auctionId);
    }

    public Category getCategoryById(Long categoryId) {
        return categoryRepository.findCategoryById(categoryId).get();
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAllCategories();
    }

    public ProfileEntity getUserByName(String username) {
        return profileRepository.findUserByUsername(username);
    }

    public String getUsername() {
        return session.getUsername();
    }
}
