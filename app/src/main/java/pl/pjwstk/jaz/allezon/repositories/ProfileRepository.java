package pl.pjwstk.jaz.allezon.repositories;

import pl.pjwstk.jaz.allezon.entities.ProfileEntity;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@ApplicationScoped
public class ProfileRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void addUserToDatabase(ProfileEntity user) {
        entityManager.persist(user);
    }

    @Transactional
    public boolean checkIfUserExists(String username) {
        final ProfileEntity user = entityManager.find(ProfileEntity.class, username);
        return (user != null);
    }

    @Transactional
    public ProfileEntity getUser(String username) {
        return entityManager.find(ProfileEntity.class, username);
    }
}