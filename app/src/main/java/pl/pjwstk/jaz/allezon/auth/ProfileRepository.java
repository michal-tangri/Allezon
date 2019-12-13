package pl.pjwstk.jaz.allezon.auth;

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
    public ProfileEntity findUser(String username) {
        return entityManager.find(ProfileEntity.class, username);
    }
}