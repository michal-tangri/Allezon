package pl.pjwstk.jaz.allezon.webapp.sales.carts;

import pl.pjwstk.jaz.allezon.webapp.authorization.repositories.ProfileRepository;
import pl.pjwstk.jaz.allezon.webapp.sales.auctions.entities.Auction;
import pl.pjwstk.jaz.allezon.webapp.sales.auctions.repositories.AuctionRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class CartManagerService {

    @Inject
    private CartRepository cartRepository;

    @Inject
    private AuctionRepository auctionRepository;

    @Inject
    private ProfileRepository profileRepository;

    public void createCart(final String username) {
        cartRepository.createCartForUser(username);
    }

    public Cart getCartByUsername(final String username) {
        return cartRepository.findCartByUsername(username);
    }

    public Auction getAuctionById(final Long id) {
        return auctionRepository.findAuctionById(id).orElse(null);
    }

    public void saveProduct(final CartProduct product) {
        cartRepository.save(product);
    }

    public void deleteCart(String username) {
        cartRepository.deleteCart(username);
    }

    public boolean checkIfUserExists(final String username) {
        return profileRepository.findUserByUsername(username) != null;
    }
}