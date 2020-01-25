package pl.pjwstk.jaz.allezon.webapp.sales.carts;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import java.time.LocalDate;

@ApplicationScoped
public class CartApiImpl implements CartApi {

    @Inject
    private CartManagerService cartManagerService;

    @Override
    public Cart getCartWithProducts(final String username) throws IllegalArgumentException{
        var cart = cartManagerService.getCartByUsername(username);

        if(cart == null)
            throw new IllegalArgumentException("This user does not exist or does not have a cart!");

        if(LocalDate.now().minusDays(30).isAfter(cart.getCreatedAt())) {
            deleteCart(username);
            throw new IllegalArgumentException("This cart has expired");
        }

        return cart;
    }

    @Override
    public void addItemToCart(@Valid final AddItemCommand addItemCommand) throws IllegalArgumentException {

        final String username = addItemCommand.getUsername();
        final Long auctionId = addItemCommand.getAuctionId();

        Cart cart = cartManagerService.getCartByUsername(username);
        if(cart == null) {
            if(cartManagerService.checkIfUserExists(username))
                cartManagerService.createCart(username);
            else
                throw new IllegalArgumentException("User \"" + username + "\" does not exist");
            cart = cartManagerService.getCartByUsername(username);
        }

        var auction = cartManagerService.getAuctionById(auctionId);
        if(auction == null)
            throw new IllegalArgumentException("Auction with this ID does not exist");

        CartProduct product = new CartProduct(cartManagerService.getAuctionById(auctionId), cart, addItemCommand.getAmount());
        cartManagerService.saveProduct(product);
    }

    @Override
    public void deleteCart(final String username) throws IllegalArgumentException {
        var cart = cartManagerService.getCartByUsername(username);
        if(cart == null)
            throw new IllegalArgumentException("This user does not exist or does not have a cart!");
        cartManagerService.deleteCart(username);
    }
}
