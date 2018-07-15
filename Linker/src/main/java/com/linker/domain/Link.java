package com.linker.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "links")
public class Link {

	@GeneratedValue
	@Id
	private Long id;

	@Column(nullable = false, length = 256)
	private String originalLink;

	@Column(unique=true, length = 16)
	private String shortLink;

	public Link() {

	}

	public Link(Long id, String originalLink, String shortLink) {
		super();
		this.id = id;
		this.originalLink = originalLink;
		this.shortLink = shortLink;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOriginalLink() {
		return originalLink;
	}

	public void setOriginalLink(String originalLink) {
		this.originalLink = originalLink;
	}

	public String getShortLink() {
		return shortLink;
	}

	public void setShortLink(String shortLink) {
		this.shortLink = shortLink;
	}

	@Override
	public String toString() {
		return "Link [id=" + id + ", originalLink=" + originalLink + ", shortLink=" + shortLink + "]";
	}

}
