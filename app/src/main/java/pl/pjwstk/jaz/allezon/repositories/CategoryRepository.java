package pl.pjwstk.jaz.allezon.repositories;

import pl.pjwstk.jaz.allezon.entities.sections.Category;
import pl.pjwstk.jaz.allezon.entities.sections.Section;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Named
@ApplicationScoped
public class CategoryRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void addCategory(Category category) {
        entityManager.persist(category);
    }

    @Transactional
    public void editCategory(String name, String newName, Section section) {
        Category category = getCategory(name);
        category.setName(newName);
        category.setSection(section);
        entityManager.merge(category);
    }

    @Transactional
    public Category getCategory(String name) {
        return entityManager.createQuery("from Category where name = :name", Category.class)
                .setParameter("name", name).getSingleResult();
    }

    @Transactional
    public List<Category> getCategories() {
        return entityManager.createQuery("from Category order by name", Category.class).getResultList();
    }
}
