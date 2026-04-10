package com.fooddelivery.util;

import com.fooddelivery.model.Category;
import com.fooddelivery.model.ComboItem;
import com.fooddelivery.model.MenuItem;

public class Catalog {
	public static final MenuItem[] MENU;
	static {
		MENU = new MenuItem[] { 
				new MenuItem("Paneer Butter Masala", 250, Category.MAIN_COURSE),
				new MenuItem("Veg Burger", 120, Category.SNACKS), 
				new MenuItem("Cold Coffee", 80, Category.BEVERAGE),
				new ComboItem("Family Combo", 600, Category.MAIN_COURSE, 10) 
				};
	}
}
