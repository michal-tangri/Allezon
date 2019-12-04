package pl.pjwstk.jaz.allezon.repositories;

import pl.pjwstk.jaz.allezon.entities.sections.Section;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Named
@ApplicationScoped
public class SectionRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void addSection(Section section) {
        entityManager.persist(section);
    }

    @Transactional
    public Section getSection(String name) {
        return entityManager.createQuery("from Section where name = :name", Section.class)
                .setParameter("name", name).getSingleResult();
    }

    @Transactional
    public void editSection(String name, String newName) {
        Section section = getSection(name);
        section.setName(newName);
        entityManager.merge(section);
    }

    @Transactional
    public List<Section> getSections() {
        return entityManager.createQuery("from Section order by name", Section.class).getResultList();
    }
}