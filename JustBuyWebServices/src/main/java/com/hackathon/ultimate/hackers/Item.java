package com.hackathon.ultimate.hackers;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * This class represents Item table.
 * 
 * @author asif
 */
@Entity
@Table(name = "Item")
@Getter
@Setter
@EqualsAndHashCode(exclude = "id")
@ToString
public class Item {
	@Id
	@GeneratedValue
	@Column(name = "id", nullable = false)
	private int id;

	/**
	 * Title of item.
	 */
	@Column(name = "title", nullable = false, length = 30)
	private String title;

	/**
	 * Description of item.
	 */
	@Column(name = "description", nullable = false, length = 400)
	private String description;

	/**
	 * Value or cost of item.
	 */
	@Column(name = "value", nullable = false)
	private int value;

	/**
	 * Time on which item was created.
	 */
	@Column(name = "created_on", nullable = false)
	private Date time;
	
	/**
	 * Created by user.
	 */
	@JoinColumn(name = "created_by", nullable = false)
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private User createdBy;
	
	/**
	 * File path of picture representing item.
	 */
	@Column(name = "file_path")
	private String filePath;
	
	/**
	 * If item is sold out. <code>true</code>if item is sold out else
	 * <code>false</code>
	 */
	@Column(name = "is_sold_out", nullable = false)
	private boolean isSoldOut;

	/**
	 * Type of item.
	 */
	@Column(name = "item_type", nullable = false)
	private ItemType type;

	/**
	 * Default constructor for hibernate.
	 */
	private Item() {
	}

	public Item(final String title, final String description, final int value,
			final Date time, final User createdBy, final String filepath,
			final boolean isSoldOut, final ItemType type) {
		super();
		this.title = title;
		this.description = description;
		this.value = value;
		this.time = (Date) time.clone();
		this.createdBy = createdBy;
		this.filePath = filepath;
		this.isSoldOut = isSoldOut;
		this.type = type;
	}

	/**
	 * Get item creation time.
	 * 
	 * @return
	 */
	public Date getTime() {
		return (Date) this.time.clone();
	}

}
