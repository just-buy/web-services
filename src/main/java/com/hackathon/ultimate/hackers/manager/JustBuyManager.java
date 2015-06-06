package com.hackathon.ultimate.hackers.manager;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.hackathon.ultimate.hackers.Category;
import com.hackathon.ultimate.hackers.Item;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * JustBuyManager to manage all entities.
 *
 * @author asif
 */
@Singleton
@Slf4j
@RequiredArgsConstructor(onConstructor = @_(@Inject))
public class JustBuyManager {

    /**
     * Hibernate Session Factory.
     */
    private final SessionFactory sessionFactory;

    @SuppressWarnings("unchecked")
    public List<Category> getCategories() throws Exception {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            List<Category> categories = session.createQuery("FROM Category")
                    .list();
            tx.commit();
            log.debug("Successfully queried all categories {}",
                    categories.size());
            return categories;
        } catch (HibernateException e) {
            if (tx != null)
                tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public List<Item> getItemsInCategory(final Integer categoryId)
            throws Exception {
        Category category = getCategory(categoryId);
        return new ArrayList<>(category.getItems());
    }

    public Category getCategory(final Integer categoryId) throws Exception {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Category category = (Category) session.get(Category.class,
                    categoryId);
            tx.commit();
            log.debug("Successfully queried category {}", category);
            return category;
        } catch (HibernateException e) {
            if (tx != null)
                tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    public Item getItem(final Integer itemId) throws Exception {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Item item = (Item) session.get(Item.class, itemId);
            tx.commit();
            log.debug("Successfully queried item {}", item);
            return item;
        } catch (HibernateException e) {
            if (tx != null)
                tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    public Integer createItem(final Item item) throws Exception {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            int id = (int) session.save(item);
            tx.commit();
            log.debug("Successfully created user {}", item);
            return id;
        } catch (HibernateException e) {
            if (tx != null)
                tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    public Integer createCategory(final Category category) throws Exception {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            int id = (int) session.save(category);
            tx.commit();
            log.debug("Successfully created category {}", category);
            return id;
        } catch (HibernateException e) {
            if (tx != null)
                tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    public void addItemToCategory(final Item item, final int categoryId) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Category category = (Category) session.get(Category.class,
                    categoryId);
            List<Item> categoryItems = new ArrayList<>(category.getItems());
            categoryItems.add(item);
            category.setItems(new HashSet<>(categoryItems));
            session.update(category);
            tx.commit();
            log.debug("Successfully added item to {} category {}", item,
                    categoryId);
        } catch (HibernateException e) {
            if (tx != null)
                tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}
