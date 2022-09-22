package com.ahmet.e_commerce_ulti_backend;

import com.ahmet.e_commerce_ulti_backend.entities.Category;
import com.ahmet.e_commerce_ulti_backend.repositories.CategoryRep;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class CategoryRepTest {

    @Autowired
    private CategoryRep categoryRep;

    @Test
    public void testCreateRootCategory() {
        Category category = new Category("Electronics");
        Category savedCategory = categoryRep.save(category);

        assertThat(savedCategory.getId()).isGreaterThan(0);
    }

    @Test
    public void testCreateSubCategory() {
        Category parent = new Category(5L);
        Category memories = new Category("Memories", parent);
        Category rams = new Category("Rams", parent);
        categoryRep.saveAll(List.of(memories, rams));

    }

    @Test
    public void testGetCategory() {
        Category category = categoryRep.findById(2L).get();
        System.out.println(category.getName());
        System.out.println("--------------------");
        List<Category> children = category.getChildren();

        for (Category c : children) {
            System.out.println(c.getName());
        }

        assertThat(children.size()).isGreaterThan(0);
    }

    @Test
    public void testHierarchicalCategories() {
        List<Category> categoryList = categoryRep.findAll();

        for (Category c : categoryList) {
            if (c.getParent() == null) {
                System.out.println("-" + c.getName());
                System.out.println("-------------");
                List<Category> children = c.getChildren();
                for (Category child : children) {
                    System.out.println("--" + child.getName());

                    if (child.getChildren() != null) {
                        List<Category> grandSons = child.getChildren();
                        for (Category grandsn : grandSons) {
                            System.out.println("---" + grandsn.getName());
                        }
                    }
                }
            }
        }
    }
}
