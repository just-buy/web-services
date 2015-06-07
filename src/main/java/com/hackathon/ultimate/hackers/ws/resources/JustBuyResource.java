package com.hackathon.ultimate.hackers.ws.resources;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.hackathon.ultimate.hackers.Category;
import com.hackathon.ultimate.hackers.Item;
import com.hackathon.ultimate.hackers.ItemType;
import com.hackathon.ultimate.hackers.User;
import com.hackathon.ultimate.hackers.config.HibernateModule;
import com.hackathon.ultimate.hackers.manager.IdentityManager;
import com.hackathon.ultimate.hackers.manager.JustBuyManager;
import com.hackathon.ultimate.hackers.utility.PasswordHasher;
import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Date;
import java.util.List;

/**
 * @author asif
 */
@Path("/")
@Slf4j
public class JustBuyResource {

    private final IdentityManager identityManager;
    private final JustBuyManager justBuyManager;

    public JustBuyResource() {
        super();
        Injector injector = Guice.createInjector(new HibernateModule());
        justBuyManager = injector.getInstance(JustBuyManager.class);
        identityManager = injector.getInstance(IdentityManager.class);
        /*
         * Injector injector = Guice.createInjector(new HibernateModule());
         * final JustBuyManager justBuyManager = injector
         * .getInstance(JustBuyManager.class); final IdentityManager
         * identityManager = injector .getInstance(IdentityManager.class);
         */
        try {
            // Initializing users info
            User user;
            user = new User("Asif Ahammed", "+919491259503",
                    "Koramangala 3rd Block, Banaglore",
                    "asifahammed@flipkart.com",
                    PasswordHasher.createHash("asif123"));
            identityManager.createUser(user);
            user = new User("Saurav Kumar Singh", "+917451269134",
                    "3rd Cross Madivala, Banaglore",
                    "saurav.singh@flipkart.com",
                    PasswordHasher.createHash("saurav123"));
            identityManager.createUser(user);
            user = new User("Aritra Singh", "+919849231543",
                    "Near Forum Mall Koramangala, Banaglore",
                    "aritra.saha@flipkart.com",
                    PasswordHasher.createHash("aritra123"));
            identityManager.createUser(user);

            // Initializing categories
            Category category = new Category("Electronics", null);
            int categoryId = justBuyManager.createCategory(category);
            category = new Category("Auto Mobiles", null);
            justBuyManager.createCategory(category);
            category = new Category("Real Estate", null);
            justBuyManager.createCategory(category);
            category = new Category("Furniture", null);
            justBuyManager.createCategory(category);
            category = new Category("Others ..", null);
            justBuyManager.createCategory(category);

            // Initializing item
            Item item;
            item = new Item("Samsung Galaxy",
                    "Brand new samsung galaxy available", 200, new Date(),
                    user, "~/ads/item_1.png", false, ItemType.SELL);
            justBuyManager.addItemToCategory(item, 1);
            item = new Item("Nexus 5",
                    "Nexus 5 better than MI 3", 2000, new Date(),
                    user, "~/ads/item_2.png", false, ItemType.SELL);
            justBuyManager.addItemToCategory(item, 2);
        } catch (Exception e) {
            // do nothing
        }
    }

    /**
     * Method is used to test the simplest functionality of a resource.
     *
     * @return a string "Hello world"
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getGreeting() {
        return "Rest Services of justbuy are Up!";
    }

    /**
     * Method is used to get item.
     *
     * @param id
     * @return Item
     * @throws Exception
     */
    @GET
    @Path("/item/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Item getItem(@PathParam(value = "id") Integer id)
            throws Exception {
        return justBuyManager.getItem(id);
    }

    /**
     * Method is used to get all categories.
     *
     * @return List of categories
     * @throws Exception
     */
    @GET
    @Path("/category")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Category> getCategories() throws Exception {
        List<Category> categories = justBuyManager.getCategories();
        log.info("Categories are {}", categories);
        return categories;
    }

    /**
     * Method is used to get category.
     *
     * @param id
     * @return Category
     * @throws Exception
     */
    @GET
    @Path("/category/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Category getCategory(
            @PathParam(value = "id") Integer id)
            throws Exception {
        final Category category = justBuyManager.getCategory(id);
        log.info("Category is {}", category);
        return category;
    }

    /**
     * Method is used to get items in a category.
     *
     * @param id
     * @return List of Items
     * @throws Exception
     */
    @GET
    @Path("/category/{id}/item")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Item> getItemsInCategory(
            @PathParam(value = "id") Integer id)
            throws Exception {
        List<Item> itemsInCategory = justBuyManager
                .getItemsInCategory(id);
        log.info("Category ID {}", id);
        log.info("items size {}", itemsInCategory.size());
        log.info("Items in category {} are {}", id, itemsInCategory);
        return itemsInCategory;
    }

    /**
     * Method is used to add item to category.
     *
     * @param item
     * @param categoryId
     * @throws Exception
     */
    @POST
    @Path("/category/{id}/item")
    @Consumes(MediaType.APPLICATION_JSON)
    public void register(Item item,
                         @PathParam(value = "id") Integer categoryId)
            throws Exception {
        justBuyManager.addItemToCategory(item, categoryId);
    }

    /**
     * Method is used to get user.
     *
     * @param id
     * @throws Exception
     */
    @GET
    @Path("/user/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public User getUser(@PathParam(value = "id") Integer id)
            throws Exception {
        return identityManager.getUser(id);
    }

    /**
     * Method is used to login.
     *
     * @param email
     * @param password
     * @return <code>true</code> if user authentication is valid.
     * <code>false</code>
     */
    @POST
    @Path("/user/login")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public boolean authenticate(@FormParam("email") String email,
                                @FormParam("password") String password) {
        String correctHash;
        try {
            correctHash = identityManager.getUser(email)
                    .getPassword();
            return PasswordHasher.validatePassword(password,
                    correctHash);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Method is used to create user.
     *
     * @param user
     * @throws Exception
     */
    @POST
    @Path("/user/register")
    @Consumes(MediaType.APPLICATION_JSON)
    public void register(User user) throws Exception {
        user.setPassword(PasswordHasher.createHash(user.getPassword()));
        identityManager.createUser(user);
    }
}
