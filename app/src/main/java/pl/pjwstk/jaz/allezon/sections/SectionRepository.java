package pl.pjwstk.jaz.allezon.sections;

import pl.pjwstk.jaz.allezon.entities.sections.Section;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Named
@ApplicationScoped
public class SectionRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void save(Section section) throws Exception {
        if(findSectionByName(section.getName()) == null) {
            if(section.getId() == null)
                entityManager.persist(section);
            else
                entityManager.merge(section);
        }
        else
            throw new Exception();
    }

    @Transactional
    public Optional<Section> findSectionById(Long id) {
        var section = entityManager.find(Section.class, id);
        return Optional.ofNullable(section);
    }

    @Transactional
    public Section findSectionByName(String name) {
        var results =  entityManager.createQuery("from Section where name = :name", Section.class)
                .setParameter("name", name);

        if(!results.getResultList().isEmpty())
            return results.getSingleResult();
        else
            return null;
    }

    @Transactional
    public List<Section> findAll() {
        return entityManager.createQuery("from Section order by name", Section.class).getResultList();
    }
}