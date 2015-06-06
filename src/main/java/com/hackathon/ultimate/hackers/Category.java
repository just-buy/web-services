package com.hackathon.ultimate.hackers;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

/**
 * This class represents Category table.
 *
 * @author asif
 */

@Entity
@Table(name = "Category")
@Getter
@Setter
@EqualsAndHashCode(exclude = "id")
@ToString
public class Category {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    /**
     * Category Name.
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * Items in the category.
     */
    @JoinColumn(name = "category_id")
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Item> items;

    /**
     * Default constructor for hibernate.
     */
    private Category() {
    }

    public Category(String name, Set<Item> items) {
        super();
        this.name = name;
        this.items = items;
    }

}
