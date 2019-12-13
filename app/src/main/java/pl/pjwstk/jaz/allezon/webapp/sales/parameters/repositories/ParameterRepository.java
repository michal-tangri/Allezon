package pl.pjwstk.jaz.allezon.webapp.sales.parameters.repositories;

import pl.pjwstk.jaz.allezon.webapp.sales.parameters.entities.AuctionParameter;
import pl.pjwstk.jaz.allezon.webapp.sales.parameters.entities.AuctionParameterId;
import pl.pjwstk.jaz.allezon.webapp.sales.parameters.entities.Parameter;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class ParameterRepository {

    @PersistenceContext
    private EntityManager entityManager;

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
}
