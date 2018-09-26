/**
 * 
 */
package com.green.finale.model;

import com.green.finale.entity.Image;

/**
 * @author duypham
 *
 */
public class ImageModel {
	private String id;
	private String location;
	private long keyId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public long getKeyId() {
		return keyId;
	}

	public void setKeyId(long keyId) {
		this.keyId = keyId;
	}
	
	public void fromImage(Image image) {
		this.setId(image.getId());
		this.setLocation(image.getLocation());
		this.setKeyId(image.getKeyId());
	}
	
	public Image toImage() {
		Image image = new Image();
		image.setId(this.getId());
		image.setLocation(this.getLocation());
		image.setKeyId(this.getKeyId());
		return image;
	}

}
