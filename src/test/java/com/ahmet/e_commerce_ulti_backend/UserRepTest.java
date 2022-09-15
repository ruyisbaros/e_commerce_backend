package com.ahmet.e_commerce_ulti_backend;

import com.ahmet.e_commerce_ulti_backend.appUser.AppUser;
import com.ahmet.e_commerce_ulti_backend.entities.Role;
import com.ahmet.e_commerce_ulti_backend.repositories.AppUserRep;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class UserRepTest {

    @Autowired
    private AppUserRep appUserRep;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    public void createUserWithSingleRole() {
        Role roleAdmin = testEntityManager.find(Role.class, "Assistant");
        AppUser user1 = new AppUser("baran", "erdonmez", "b@a.com", "12345");
        user1.addRole(roleAdmin);
        AppUser savedUser = appUserRep.save(user1);
        assertThat(savedUser.getId()).isGreaterThan(0);
    }

    @Test
    public void createUserDoubleRole() {
        Role roleAssistant = testEntityManager.find(Role.class, "Assistant");
        Role roleEditor = testEntityManager.find(Role.class, "Editor");
        AppUser user1 = new AppUser("hulya", "erdonmez", "h@a.com", "12345");
        user1.addRole(roleAssistant);
        user1.addRole(roleEditor);
        AppUser savedUser = appUserRep.save(user1);
        assertThat(savedUser.getId()).isGreaterThan(0);
    }

    @Test
    public void getAllUsers() {
        List<AppUser> users = appUserRep.findAll();

        for (AppUser u : users) {
            System.out.println(u);
        }
    }

    @Test
    public void getAUserById() {
        System.out.println(appUserRep.findById(1L));
    }

    @Test
    public void testUpdateUser() {
        AppUser updatedUser = appUserRep.findById(1L).get();
        updatedUser.setFirstName("updatedAhmet");
        updatedUser.setEnabled(true);
        appUserRep.save(updatedUser);
    }

    @Test
    public void testUpdateRole(){
        AppUser updatedUser = appUserRep.findById(1L).get();
        Role roleSalesPerson = testEntityManager.find(Role.class, "SalesPerson");
        updatedUser.addRole(roleSalesPerson);
        appUserRep.save(updatedUser);
    }

    @Test
    public void testDeleteUserById(){
        appUserRep.deleteById(1L);
    }
}
