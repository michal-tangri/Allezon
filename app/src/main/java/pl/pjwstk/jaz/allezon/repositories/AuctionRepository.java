package pl.pjwstk.jaz.allezon.repositories;

import pl.pjwstk.jaz.allezon.entities.auctions.AuctionEntity;
import pl.pjwstk.jaz.allezon.entities.auctions.ParameterEntity;

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
    public void addAuction(AuctionEntity auction) {
        entityManager.persist(auction);
    }

    @Transactional
    public ParameterEntity getParameter(String name) {
        return entityManager.createQuery("from ParameterEntity where name = :name", ParameterEntity.class)
                .setParameter("name", name).getSingleResult();
    }

    @Transactional
    public List<ParameterEntity> getParameters() {
        return entityManager.createQuery("from ParameterEntity order by name", ParameterEntity.class)
                .getResultList();
    }

}