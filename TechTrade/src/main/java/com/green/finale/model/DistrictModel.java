/**
 * 
 */
package com.green.finale.model;

import com.green.finale.entity.City;
import com.green.finale.entity.District;

/**
 * @author duypham
 *
 */
public class DistrictModel {
	private String id;
	private String name;
	private City city;

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

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}
	
	public void fromDistrict(District district) {
		this.setId(district.getId());
		this.setName(district.getName());
		this.setCity(district.getCity());
	}
	
	public District toDistrict() {
		District district = new District();
		district.setId(this.getId());
		district.setName(this.getName());
		district.setCity(this.getCity());
		return district;
	}

}
