package pl.pjwstk.jaz.allezon.auctions;

import pl.pjwstk.jaz.allezon.auth.ProfileEntity;
import pl.pjwstk.jaz.allezon.entities.auctions.*;
import pl.pjwstk.jaz.allezon.entities.sections.Category;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Named
@ApplicationScoped
public class AuctionRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void save(Auction auction) {
        entityManager.merge(auction);
    }

    @Transactional
    public Optional<Auction> findAuctionById(Long id) {
        var auction = entityManager.find(Auction.class, id);
        return Optional.ofNullable(auction);
    }

    @Transactional
    public List<Auction> findAllAuctionsByCategory(String name) {
        return entityManager.createQuery("from Auction where category.name = :name", Auction.class)
                .setParameter("name", name).getResultList();
    }

    @Transactional
    public List<Auction> findAllAuctionsByUsername(String username) {
        return entityManager.createQuery("from Auction where profile.username = :username", Auction.class)
                .setParameter("username", username).getResultList();
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
    public AuctionParameter findAuctionParameterById(Long parameterId, Long auctionId) {
        return entityManager.find(AuctionParameter.class, new AuctionParameterId(parameterId, auctionId));
    }

    @Transactional
    public List<Photo> findAllPhotosByAuctionId(Long auctionId) {
        return entityManager.createQuery("from Photo where auction.id = :auctionId ORDER BY id", Photo.class)
                .setParameter("auctionId", auctionId).getResultList();
    }


    @Transactional
    public ProfileEntity findUserByName(String username) {
        return entityManager.find(ProfileEntity.class, username);
    }

}