package pl.pjwstk.jaz.allezon.webapp.sales.carts;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class CartRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void save(CartProduct product) {
        entityManager.persist(product);
    }

    @Transactional
    public Cart findCartByUsername(String username) {
         List<Cart> data = entityManager.createQuery("from Cart WHERE username = :username", Cart.class)
                .setParameter("username", username).getResultList();
         return data.isEmpty() ? null : data.get(0);
    }

    @Transactional
    public void removeProductsInUsersCart(String username) {
        Long cartId = findCartByUsername(username).getId();
        entityManager.createNativeQuery("DELETE FROM cart_products WHERE cart_id = " + cartId).executeUpdate();

    }
}
