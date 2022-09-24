package com.ahmet.e_commerce_ulti_backend.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "categories")
@AllArgsConstructor
@NoArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 128, unique = true)
    private String name;
    @Column(nullable = false, length = 64, unique = true)
    private String alias;
    private boolean enabled;

    @OneToOne()
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Category> children = new ArrayList<>();

    @OneToMany(fetch = FetchType.EAGER)
    private List<CategoryImage> images;

    public Category(Long id) {
        this.id = id;
    }

    public Category(String name) {
        this.name = name;
        this.alias = name;
    }

    public Category(String name, Category parent) {
        this.name = name;
        this.alias = name;
        this.parent = parent;
    }
}
