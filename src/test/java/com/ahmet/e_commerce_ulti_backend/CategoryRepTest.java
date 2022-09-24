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
    public void testCreateSubCategory1() {
        Category parent = new Category(1L);
        Category notebooks = new Category("Notebooks", parent);
        Category desktops = new Category("Desktops", parent);
        categoryRep.saveAll(List.of(notebooks, desktops));

    }
    @Test
    public void testCreateSubCategory2() {
        Category parent = new Category(2L);
        Category cameras = new Category("Cameras", parent);
        Category sf = new Category("Smart Phones", parent);
        Category sf2 = new Category("TV", parent);
        categoryRep.saveAll(List.of(cameras, sf,sf2));
    }
    @Test
    public void testCreateSubCategory3() {
        Category parent = new Category(3L);
        Category cameras = new Category("Memories", parent);
        Category sf = new Category("RAMs", parent);
        categoryRep.saveAll(List.of(cameras, sf));
    }

    @Test
    public void testNewCategoryOne(){
        Category parent = new Category(1L);
        Category desktops = new Category("Desktops", parent);
        Category saved = categoryRep.save(desktops);
        assertThat(saved.getId()).isGreaterThan(0);
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
    public void testListRootCategories(){
    List<Category> rootCategories=categoryRep.findRootCategories();
    rootCategories.forEach(category -> System.out.println(category.getName()));
    }

    @Test
    public void testHierarchicalCategories() {
        List<Category> categoryList = categoryRep.findAll();

        for (Category category : categoryList) {
            if (category.getParent() == null) {
                System.out.println("-" + category.getName());
                System.out.println("-------------");
                List<Category> children = category.getChildren();
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
