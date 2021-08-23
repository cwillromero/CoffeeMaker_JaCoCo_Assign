package edu.ncsu.csc326.coffeemaker;

import junit.framework.TestCase;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc326.coffeemaker.exceptions.InventoryException;
import edu.ncsu.csc326.coffeemaker.exceptions.RecipeException;


/**
 * 
 * @author Sarah Heckman
 *
 * Extended by Mike Whalen
 *
 * Unit tests for CoffeeMaker class.
 */

public class CoffeeMakerTest extends TestCase {
	
	private Recipe r1;
	private Recipe r2;
	private Recipe r3;
	private Recipe r4;
	private Recipe r5;
	private Recipe r6;
	private CoffeeMaker cm;
	private RecipeBook recipeBookStub;
	private Recipe [] stubRecipies; 
	
	protected void setUp() throws Exception {
		
		cm = new CoffeeMaker();
		
		//Set up for r1
		r1 = new Recipe();
		r1.setName("Coffee");
		r1.setAmtChocolate("0");
		r1.setAmtCoffee("3");
		r1.setAmtMilk("1");
		r1.setAmtSugar("1");
		r1.setPrice("50");
		
		//Set up for r2
		r2 = new Recipe();
		r2.setName("Mocha");
		r2.setAmtChocolate("20");
		r2.setAmtCoffee("3");
		r2.setAmtMilk("1");
		r2.setAmtSugar("1");
		r2.setPrice("75");
		
		//Set up for r3
		r3 = new Recipe();
		r3.setName("Latte");
		r3.setAmtChocolate("0");
		r3.setAmtCoffee("3");
		r3.setAmtMilk("3");
		r3.setAmtSugar("1");
		r3.setPrice("100");
		
		//Set up for r4
		r4 = new Recipe();
		r4.setName("Hot Chocolate");
		r4.setAmtChocolate("4");
		r4.setAmtCoffee("0");
		r4.setAmtMilk("1");
		r4.setAmtSugar("1");
		r4.setPrice("65");
		
		//Set up for r5 (added by MWW)
		r5 = new Recipe();
		r5.setName("Super Hot Chocolate");
		r5.setAmtChocolate("6");
		r5.setAmtCoffee("0");
		r5.setAmtMilk("1");
		r5.setAmtSugar("1");
		r5.setPrice("100");
		
		//Set up for r5 (added by MWW)
		r6 = new Recipe();
		r6.setName("Super Cafeine");
		r6.setAmtChocolate("0");
		r6.setAmtCoffee("50");
		r6.setAmtMilk("0");
		r6.setAmtSugar("1");
		r6.setPrice("150");

		stubRecipies = new Recipe [] {r1, r2, r3};
		
		super.setUp();
	}
	

	/* ----------------------------- Add Recipe ----------------------------- */
	/**
	 * Given a coffee maker with no recipes
	 * When we add 3 recipes
	 * Then we do not get an exception trying to so 
	 * 
	 * @throws InventoryException  if try to add more than 3 recipes.
	 */
	@Test
	public void testAddRecipes() throws Exception {
		cm.addRecipe(r1);
		cm.addRecipe(r2);
		cm.addRecipe(r3);
	}
	/**
	 * Given a coffee maker with no recipes
	 * When we add 4 or more recipes
	 * Then get an exception trying to so 
	**/
	@Test
	public void testAddMoreThan3Recipes(){
		cm.addRecipe(r1);
		cm.addRecipe(r2);
		cm.addRecipe(r3);
		boolean added;
		try {
			added = cm.addRecipe(r4);
		}catch(Exception e) {
			added= false;
		}
		assertEquals(false, added);

	}
	
	
	/* ----------------------------- Add Inventory ----------------------------- */
	/**
	 * Given a coffee maker with the default inventory
	 * When we add inventory with well-formed quantities
	 * Then we do not get an exception trying to read the inventory quantities.
	 * 
	 * @throws InventoryException  if there was an error parsing the quanity
	 * 		to a positive integer.
	 */
	@Test
	public void testAddInventory() throws InventoryException {
		cm.addInventory("4","7","1","9");
	}
	
	/**
	 * Given a coffee maker with the default inventory
	 * When we add inventory with malformed quantities (i.e., a negative 
	 * quantity and a non-numeric string)
	 * Then we get an inventory exception
	 * 
	 * @throws InventoryException  if there was an error parsing the quanity
	 * 		to a positive integer.
	 */
	@Test(expected = InventoryException.class)
	public void testAddInventoryException() throws InventoryException {
		cm.addInventory("4", "-1", "asdf", "3");
	}
	
	@Test
	public void testAddCoffeeToInventory() throws InventoryException {
		cm.addInventory("1","0","0","0");
	}
	
	@Test
	public void testAddMilkToInventory() throws InventoryException {
		cm.addInventory("0","1","0","0");
	}
	
	@Test
	public void testAddSugarToInventory() throws InventoryException {
		cm.addInventory("0","0","1","0");
	}
	
	@Test
	public void testAddChocolateToInventory() throws InventoryException {
		cm.addInventory("0","0","0","1");
	}
	
	/* ----------------------------- Make Coffee ----------------------------- */
	
	/**
	 * Given a coffee maker with one valid recipe
	 * When we make coffee, selecting the valid recipe and paying more than 
	 * 		the coffee costs
	 * Then we get the correct change back.
	 */
	@Test
	public void testMakeCoffee() {
		cm.addRecipe(r1);
		assertEquals(25, cm.makeCoffee(0, 75));
	}
	
	/**
	 * Given a coffee maker with one valid recipe
	 * When we make coffee, selecting the valid recipe and paying less than 
	 * 		the coffee costs
	 * Then we get a the same amount we put.
	 */
	@Test
	public void testMakeCoffeeLessMoney() {
		cm.addRecipe(r1);
		assertEquals(1, cm.makeCoffee(0, 1));
	}
	
	/**
	 * Given a coffee maker with one invalid recipe less than 0
	 * When we make coffee, selecting the recipe and paying
	 * Then we get an exception.
	 */
	@Test
	public void testMakeCoffeeNegativeRecipe() {
		int change;
		try {
			change = cm.makeCoffee(-1, 56);
		}catch(Exception e) {
			change = 0;
		}
		assertEquals(56, change);
	}
	/**
	 * Given a coffee maker with one invalid recipe less than 0
	 * When we make coffee, selecting the recipe and paying
	 * Then we get an exception.
	 */
	@Test
	public void testMakeCoffeeOutOfIndexRecipe() {
		int change;
		try {
			change = cm.makeCoffee(100, 56);
		}catch(Exception e) {
			change = 0;
		}
		assertEquals(56, change);
	}
	/**
	 * Given a coffee maker with one valid recipe
	 * When we make coffee, selecting the valid recipe and paying with non-number value
	 * Then we get a random integer as change.
	 */
	@Test
	public void testMakeCoffeeInvalidPayment() {
		assertEquals(115, cm.makeCoffee(0, 's'));
	}
	
	/**
	 * Given a coffee maker with one valid recipe but with more ingredientes than the machine have
	 * When we make coffee, selecting the valid recipe and paying a valid value
	 * Then we get the whole money back.
	 */
	@Test
	public void testMakeCoffeeTooMuchIngredients() {
		cm.addRecipe(r6);
		assertEquals(176, cm.makeCoffee(0, 176));
	}

	/* ----------------------------- Check Inventory ----------------------------- */
	
	/**
	 * Given a coffee maker with one valid recipe
	 * When we make coffee, selecting the valid recipe and paying with a valid value
	 * And  we check the inventory, it should be less than 15 units per ingredient 
	 * Then we get an exception.
	 */
	@Test
	public void testCheckInventory() {
		cm.addRecipe(r1);
		cm.makeCoffee(0, 75);
		assertEquals("Coffee: 12\n"
				+ "Milk: 14\n"
				+ "Sugar: 14\n"
				+ "Chocolate: 15\n", 
				cm.checkInventory());
	}
	
	/* ----------------------------- Edit Recipe ----------------------------- */
	
	/**
	 * Given a valid recipe
	 * When we edit it with a valid recipe
	 * Then we get the name of recipe we edited.
	 */
	@Test
	public void testEditRecipe() {
		cm.addRecipe(r1);
		assertEquals("Coffee", cm.editRecipe(0, r4));
	}
	/**
	 * Given an invalid recipe
	 * When we edit it with a valid recipe
	 * Then we get a null value.
	 */
	@Test
	public void testEditNegativeRecipe() throws Exception{
		cm.addRecipe(r1);
		assertEquals(null, cm.editRecipe(-1, r2));
	}
	
	/**
	 * Given an invalid recipe
	 * When we edit it with a valid recipe
	 * Then we get a null value.
	 */
	@Test
	public void testEditOutOfIndexRecipe() throws Exception{
		cm.addRecipe(r1);
		assertEquals(null, cm.editRecipe(5, r2));
	}
	
	/* ----------------------------- Delete Recipe ----------------------------- */
	
	/**
	 * Given an invalid recipe
	 * When we try to delete it
	 * Then we get a null value.
	 */
	@Test
	public void testDeleteNegativeRecipe() throws Exception {
		assertEquals(null, cm.deleteRecipe(-1));
	}
	
	/**
	 * Given an invalid recipe
	 * When we try to delete it
	 * Then we get a null value.
	 */
	@Test
	public void testDeleteOutOfIndexRecipe() throws Exception {
		assertEquals(null, cm.deleteRecipe(5));
	}
	
	/**
	 * Given an valid recipe
	 * When we try to delete it
	 * Then we get a value.
	 */
	@Test
	public void testDeleteRecipe(){
		cm.addRecipe(r1);
		assertEquals("Coffee", cm.deleteRecipe(0));
	}
	
}
