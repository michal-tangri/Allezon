package pl.pjwstk.jaz.allezon.webapp.categorization.categories.repositories;

import pl.pjwstk.jaz.allezon.webapp.categorization.categories.entities.Category;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Named
@ApplicationScoped
public class CategoryRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void save(Category category) throws Exception {
        String categoryName = category.getName().toLowerCase().replace("\\s", "");
        for (Category c : findAllCategoriesBySectionName(category.getSection().getName())) {
            if (c.getName().toLowerCase().replace("\\s", "").equals(categoryName))
                throw new Exception();
        }

        if (category.getId() == null)
            entityManager.persist(category);
        else
            entityManager.merge(category);
    }

    @Transactional
    public Optional<Category> findCategoryById(Long id) {
        var category = entityManager.find(Category.class, id);
        return Optional.ofNullable(category);
    }

    @Transactional
    public List<Category> findAllCategories() {
        return entityManager.createQuery("from Category order by section.name, name", Category.class).getResultList();
    }

    @Transactional
    public List<Category> findAllCategoriesBySectionName(String name) {
        return entityManager.createQuery("from Category where section.name = :name order by name", Category.class)
                .setParameter("name", name).getResultList();
    }
}