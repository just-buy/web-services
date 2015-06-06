package com.hackathon.ultimate.hackers.manager;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.hackathon.ultimate.hackers.Category;
import com.hackathon.ultimate.hackers.Item;
import com.hackathon.ultimate.hackers.ItemType;
import com.hackathon.ultimate.hackers.User;
import com.hackathon.ultimate.hackers.config.HibernateModule;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class JustBuyManagerTest {

    private JustBuyManager manager;
    private IdentityManager identityManager;

    @Before
    public void setUp() throws Exception {
        Injector injector = Guice.createInjector(new HibernateModule());
        manager = injector.getInstance(JustBuyManager.class);
        identityManager = injector.getInstance(IdentityManager.class);
    }

    @Test
    public void testCreateUser() throws Exception {
        final User user = new User("Asif", "9491259503", "address", "email",
                "password");
        Integer id = identityManager.createUser(user);
        assertNotNull(id);
        final User actual = identityManager.getUser(id);
        assertEquals(user, actual);
        final User actual2 = identityManager.getUser("email");
        assertEquals(user, actual2);
        final User user3 = new User("Asif", "9491259503", "address", "email2",
                "password");
        identityManager.createUser(user3);
    }

    @Test
    public void testAddItemToCategory() throws Exception {
        final User user = new User("Asif", "9491259503", "address", "email2",
                "password");
        Integer id = identityManager.createUser(user);
        assertNotNull(id);
        final User actual = identityManager.getUser(id);
        assertEquals(user, actual);

        final Category category = new Category("category", null);
        Integer categoryId = manager.createCategory(category);
        assertNotNull(categoryId);
        Category actualCategory = manager.getCategory(categoryId);
        assertEquals(0, actualCategory.getItems().size());

        final Item item = new Item("title", "description", 100, new Date(),
                user, "", false, ItemType.SELL);
        manager.addItemToCategory(item, categoryId);
        actualCategory = manager.getCategory(categoryId);
        assertEquals(1, actualCategory.getItems().size());
    }
}
