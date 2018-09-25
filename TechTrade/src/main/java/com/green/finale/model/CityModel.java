/**
 * 
 */
package com.green.finale.model;

import com.green.finale.entity.City;

/**
 * @author duypham
 *
 */
public class CityModel {
	private String id;
	private String name;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void fromCity(City city) {
		this.setId(city.getId());
		this.setName(city.getName());
	}
	
	public City toCity() {
		City city = new City();
		city.setId(this.getId());
		city.setName(this.getName());
		return city;
	}

}
