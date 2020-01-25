package pl.pjwstk.jaz.allezon.webapp.sales.carts;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@ApplicationScoped
public class CartRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void save(CartProduct newProduct) {
        CartProduct product = findProductInCartById(newProduct.getAuction().getId(), newProduct.getCart().getUsername());
        if (product != null) {
            product.setAmount(product.getAmount() + newProduct.getAmount());
            entityManager.merge(product);
        } else
            entityManager.persist(newProduct);
    }

    @Transactional
    public void createCartForUser(String username) {
        entityManager.persist(new Cart(username, LocalDate.now()));
    }

    @Transactional
    public Cart findCartByUsername(String username) {
        List<Cart> data = entityManager.createQuery("from Cart WHERE username = :username", Cart.class)
                .setParameter("username", username).getResultList();
        return data.isEmpty() ? null : data.get(0);
    }

    @Transactional
    public void deleteCart(String username) {
        entityManager.createNativeQuery("DELETE FROM carts WHERE username = :username")
                .setParameter("username", username).executeUpdate();

    }

    @Transactional
    private CartProduct findProductInCartById(Long productId, String username) {
        String query = "FROM CartProduct p WHERE p.cart.username = : username AND p.auction.id = :id";
        List<CartProduct> products = entityManager.createQuery(query, CartProduct.class)
                .setParameter("username", username).setParameter("id", productId).getResultList();
        return products.isEmpty() ? null : products.get(0);
    }

}
