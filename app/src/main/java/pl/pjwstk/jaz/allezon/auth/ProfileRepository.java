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
    public void sampleCodeWithPC() {
        var profile = new ProfileEntity();

        entityManager.persist(profile);

        //wersja z kluczem
        final ProfileEntity profileEntity = entityManager.find(ProfileEntity.class, 7L);

        //wersja bez klucza
        var list = entityManager.createQuery("from ProfileEntity where name = :name", ProfileEntity.class)
                .setParameter("name", "pjanowiak2")
                .getResultList();
        System.out.println();
    }

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