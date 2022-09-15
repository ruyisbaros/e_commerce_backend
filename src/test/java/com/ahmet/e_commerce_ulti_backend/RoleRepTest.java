package com.ahmet.e_commerce_ulti_backend;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.LIST;

import com.ahmet.e_commerce_ulti_backend.entities.Role;
import com.ahmet.e_commerce_ulti_backend.repositories.RoleRep;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class RoleRepTest {

    @Autowired
    private RoleRep roleRep;


    @Test
    public void testCreateAdminRole() {
        Role roleAdmin = new Role("Admin", "Fully authorized");
        Role savedRode = roleRep.save(roleAdmin);
        assertThat(savedRode.getRoleName());
    }

    @Test
    public void testCreateRestRoles() {
        Role roleSalesPerson = new Role("SalesPerson", "manage product price, customers, shipping, orders and sales report");
        Role roleEditor = new Role("Editor", "manage categories, " +
                "brands, products, articles and menus");
        Role roleShipper = new Role("Shipper", "manage shipments, shipment status, " +
                "view products, view orders");
        Role roleAssistant = new Role("Assistant", "manage questions and reviews ");
        roleRep.saveAll(List.of(roleSalesPerson, roleEditor, roleShipper, roleAssistant));
        //assertThat(savedRode.getRoleName());
    }
}
