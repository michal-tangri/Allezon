package pl.pjwstk.jaz.allezon.webapp.sales.photos.repositories;

import pl.pjwstk.jaz.allezon.webapp.sales.photos.entities.Photo;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class PhotoRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public List<Photo> findAllPhotosByAuctionId(Long auctionId) {
        return entityManager.createQuery("from Photo where auction.id = :auctionId ORDER BY id", Photo.class)
                .setParameter("auctionId", auctionId).getResultList();
    }
}
