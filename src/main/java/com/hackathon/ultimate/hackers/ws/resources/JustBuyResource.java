package com.hackathon.ultimate.hackers.ws.resources;

import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import lombok.extern.slf4j.Slf4j;

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
/**
 *
 * @author asif
 */
@Path("/justbuy")
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

			User user = new User("Asif Ahammed", "+919491259503",
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
			Item item = new Item("Samsung Galaxy",
					"Brand new samsung galaxy available", 200, new Date(),
					user, "~/ads/item_1.png", false, ItemType.SELL);
			justBuyManager.addItemToCategory(item, categoryId);
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
	 * @param itemId
	 * @return Item
	 * @throws Exception
	 */
    @Path("/item/{itemId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
	public Item getItem(@PathParam(value = "itemId") Integer itemId)
			throws Exception {
        return justBuyManager.getItem(itemId);
    }

	/**
	 * Method is used to get category.
	 *
	 * @param categoryId
	 * @return Category
	 * @throws Exception
	 */
	@Path("/category/{categoryId}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Category getCategory(
			@PathParam(value = "categoryId") Integer categoryId)
			throws Exception {
		final Category category = justBuyManager.getCategory(categoryId);
		log.info("Category is {}", category);
		return category;
	}

    /**
	 * Method is used to get all categories.
	 *
	 * @return List of categories
	 * @throws Exception
	 */
    @Path("/categories")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
	public List<Category> getCategories() throws Exception {
        return justBuyManager.getCategories();
    }

	/**
	 * Method is used to get items in a category.
	 *
	 * @param categoryId
	 * @return List of Items
	 * @throws Exception
	 */
	@Path("/category/items/{categoryId}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Item> getItemsInCategory(
			@PathParam(value = "categoryId") Integer categoryId)
			throws Exception {
		return justBuyManager.getItemsInCategory(categoryId);
	}

	/**
	 * Method is used to login.
	 *
	 * @return <code>true</code> if user authentication is valid.
	 *         <code>false</code>
	 */
	@Path("/login")
	@POST
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
	 * @throws Exception
	 *
	 */
	@Path("/register/user")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void register(User user) throws Exception {
		user.setPassword(PasswordHasher.createHash(user.getPassword()));
		identityManager.createUser(user);
	}

	/**
	 * Method is used to add item to category.
	 * 
	 * @throws Exception
	 *
	 */
	@Path("/new/item/{categoryId}")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void register(Item item,
			@PathParam(value = "categoryId") Integer categoryId)
			throws Exception {
		justBuyManager.addItemToCategory(item, categoryId);
	}

	/**
	 * Method is used to get user.
	 * 
	 * @throws Exception
	 *
	 */
	@Path("/user/{userId}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public User getUser(@PathParam(value = "userId") Integer userId)
			throws Exception {
		return identityManager.getUser(userId);
	}
}