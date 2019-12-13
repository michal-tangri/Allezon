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
        if(findCategoryInSectionByName(category.getName(), category.getSection().getName()) == null) {
            if(category.getId() == null)
                entityManager.persist(category);
            else
                entityManager.merge(category);
        }
        else
            throw new Exception();
    }

    @Transactional
    public Optional<Category> findCategoryById(Long id) {
        var category = entityManager.find(Category.class, id);
        return Optional.ofNullable(category);
    }

    @Transactional
    public Category findCategoryInSectionByName(String name, String sectionName) {
        var results = entityManager.createQuery("from Category where name = :name AND section.name = :sectionName", Category.class)
                .setParameter("name", name).setParameter("sectionName", sectionName);

        if(!results.getResultList().isEmpty())
            return results.getSingleResult();
        else
            return null;
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

//    @Transactional
//    public Section findSectionByName(String name) {
//        String query = "select NEW Section(c.section) FROM Category AS c WHERE c.section.name = :name GROUP BY c.section.name, c.section.id";
//        var results =  entityManager.createQuery(query, Section.class)
//                .setParameter("name", name);
//
//        if(!results.getResultList().isEmpty())
//            return results.getSingleResult();
//        else
//            return null;
//    }

}