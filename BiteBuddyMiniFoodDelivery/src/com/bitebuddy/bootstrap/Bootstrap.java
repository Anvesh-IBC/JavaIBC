package com.bitebuddy.bootstrap;

import com.bitebuddy.domain.*;
import com.bitebuddy.repository.InMemoryMenuRepository;
import com.bitebuddy.util.Config;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public final class Bootstrap {
	public static final InMemoryMenuRepository MENU_REPO = new InMemoryMenuRepository();
	public static final Config CONFIG = new Config();

	static {
		// Load config
		CONFIG.loadFromClasspath("/config.properties");

		// Load menu
		List<MenuItem> menu = new ArrayList<MenuItem>();
		try (BufferedReader br = Files.newBufferedReader(Paths.get("src/main/resources/menu.csv"),
				StandardCharsets.UTF_8)) {
			String line = br.readLine(); // header
			while ((line = br.readLine()) != null) {
				String[] parts = line.split(",");
				int id = Integer.parseInt(parts[0].trim());
				String name = parts[1].trim();
				BigDecimal price = new BigDecimal(parts[2].trim());
				boolean veg = Boolean.parseBoolean(parts[3].trim());
				CategoryType type = CategoryType.valueOf(parts[4].trim());
				Category cat = new Category(type.ordinal(), type.name(), type);
				menu.add(new MenuItem(id, name, price, veg, cat));
			}
		} catch (IOException e) {
			System.err.println("Failed to load menu.csv: " + e.getMessage());
		}
		MENU_REPO.saveAll(menu);
		System.out.println("Loaded " + menu.size() + " menu items.");
	}

	private Bootstrap() {
	}
}
