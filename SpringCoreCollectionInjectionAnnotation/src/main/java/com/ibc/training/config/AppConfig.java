package com.ibc.training.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

//	<property name="phoneNumbers">
//	<list>
//	<value>123-456-7890</value>
//	<value>098-765-4321</value>
//	</list>
//	</property>
	
	@Bean
	public List<String> phoneNumbers() {

		List<String> list = new ArrayList<>();

		list.add("123-456-7890");

		list.add("098-765-4321");

		return list;

	}

//<property name="skills">
//<set>
//<value>Java</value>
//<value>Spring</value>
//<value>Hibernate</value>
//</set>
//</property>
	
	@Bean
	public Set<String> skills() {

		Set<String> set = new HashSet<>();

		set.add("Java");

		set.add("Spring");

		set.add("Hibernate");

		return set;

	}

//	<property name="courses">
//	<map>
//	<entry key="101" value="Java Basics" />
//	<entry key="102" value="Spring Framework" />
//	</map>
//	</property>
	
	@Bean
	public Map<String, String> courses() {

		Map<String, String> map = new HashMap<>();

		map.put("101", "Java Basics");

		map.put("102", "Spring Framework");

		return map;

	}

//<property name="additionalInfo">
//<props>
//<prop key="team">Development</prop>
//<prop key="project">Spring Migration</prop>
//</props>
//</property>
	
	@Bean
	public Properties additionalInfo() {

		Properties props = new Properties();

		props.setProperty("team", "Development");

		props.setProperty("project", "Spring Migration");

		return props;

	}

}
