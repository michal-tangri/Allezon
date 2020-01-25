package pl.pjwstk.jaz.allezon.webapp.sales.carts;

import javax.validation.Valid;

public interface CartApi {

    Cart getCartWithProducts(String username);

    void addItemToCart(@Valid AddItemCommand addItemCommand);

    void deleteCart(String username);
}
