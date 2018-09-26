/**
 * 
 */
package com.green.finale.model;

import com.green.finale.entity.District;
import com.green.finale.entity.Ward;

/**
 * @author duypham
 *
 */
public class WardModel {
	private String id;
	private String name;
	private District district;

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

	public District getDistrict() {
		return district;
	}

	public void setDistrict(District district) {
		this.district = district;
	}

	public Ward toWard() {
		Ward ward = new Ward();
		ward.setId(this.getId());
		ward.setName(this.getName());
		ward.setDistrict(this.getDistrict());
		return ward;
	}

	public void fromWard(Ward ward) {
		this.setId(ward.getId());
		this.setName(ward.getName());
		this.setDistrict(ward.getDistrict());
	}

}
