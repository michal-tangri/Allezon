package pl.pjwstk.jaz.allezon.webapp.sales.auctions.repositories;

import pl.pjwstk.jaz.allezon.webapp.authorization.entities.ProfileEntity;
import pl.pjwstk.jaz.allezon.webapp.categorization.categories.entities.Category;
import pl.pjwstk.jaz.allezon.webapp.sales.auctions.entities.Auction;

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
    public List<Auction> findAllAuctionsByCategoryId(Long categoryId) {
        String query ="from Auction where category.id = :categoryId";
        return entityManager.createQuery(query, Auction.class)
                .setParameter("categoryId", categoryId).getResultList();
    }

    @Transactional
    public List<Auction> findAllAuctionsByUsername(String username) {
        return entityManager.createQuery("from Auction where profile.username = :username", Auction.class)
                .setParameter("username", username).getResultList();
    }
}