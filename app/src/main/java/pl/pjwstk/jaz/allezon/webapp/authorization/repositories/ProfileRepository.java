package pl.pjwstk.jaz.allezon.webapp.authorization.repositories;

import pl.pjwstk.jaz.allezon.webapp.authorization.entities.ProfileEntity;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@ApplicationScoped
public class ProfileRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void save(ProfileEntity user) {
        entityManager.persist(user);
    }

    @Transactional
    public ProfileEntity findUserByUsername(String username) {
        return entityManager.find(ProfileEntity.class, username);
    }
}