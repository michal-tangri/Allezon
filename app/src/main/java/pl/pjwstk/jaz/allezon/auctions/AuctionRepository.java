package pl.pjwstk.jaz.allezon.auctions;

import pl.pjwstk.jaz.allezon.auth.ProfileEntity;
import pl.pjwstk.jaz.allezon.entities.auctions.Auction;
import pl.pjwstk.jaz.allezon.entities.auctions.Parameter;
import pl.pjwstk.jaz.allezon.entities.sections.Category;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Named
@ApplicationScoped
public class AuctionRepository {

    //TODO getAllAuctions
    //TODO getUserAuctions
    //TODO editAuction

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void save(Auction auction) {
        if (auction.getId() == null) {
            entityManager.merge(auction);
        }
        else
            entityManager.merge(auction);
    }

    @Transactional
    public Category findCategoryByName(String name) {
        return entityManager.createQuery("from Category where name = :name", Category.class)
                .setParameter("name", name).getSingleResult();
    }

    @Transactional
    public List<Category> findAllCategories() {
        return entityManager.createQuery("from Category ORDER BY section.name, name", Category.class).getResultList();
    }

    @Transactional
    public Parameter findParameterByName(String name) {
        return entityManager.createQuery("from Parameter where name = :name", Parameter.class)
                .setParameter("name", name).getSingleResult();
    }

    @Transactional
    public List<Parameter> findAllParameters() {
        return entityManager.createQuery("from Parameter order by name", Parameter.class)
                .getResultList();
    }

    @Transactional
    public ProfileEntity findUserByName(String username) {
        return entityManager.find(ProfileEntity.class, username);
    }

}