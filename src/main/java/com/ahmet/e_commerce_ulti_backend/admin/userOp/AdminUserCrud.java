package com.ahmet.e_commerce_ulti_backend.admin.userOp;

import com.ahmet.e_commerce_ulti_backend.DTO.userDTO.CreateUpdateUser;
import com.ahmet.e_commerce_ulti_backend.appUser.AppUser;
import com.ahmet.e_commerce_ulti_backend.appUser.UserCsvExporter;
import com.ahmet.e_commerce_ulti_backend.appUser.UserExcelExporter;
import com.ahmet.e_commerce_ulti_backend.appUser.UserPdfExporter;
import com.ahmet.e_commerce_ulti_backend.entities.Role;
//import com.ahmet.e_commerce_ulti_backend.exception.ApiRequestException;
import com.ahmet.e_commerce_ulti_backend.repositories.AppUserRep;
import com.ahmet.e_commerce_ulti_backend.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/users")
@AllArgsConstructor
public class AdminUserCrud {

    private UserService userService;
    private AppUserRep appUserRep;

    @GetMapping("/list_all")
    public List<AppUser> listAllUsers() {
        return userService.listAllUsers();
    }

    @GetMapping("/all")
    public Page<AppUser> getUsers(
            @RequestParam(value = "pageSize", defaultValue = "5", required = false) int pageSize,
            @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo,
            @RequestParam(value = "sortDir", defaultValue = "id", required = false) String sortDir,
            @RequestParam(value = "sortField", required = false) String sortField,
            @RequestParam(value = "keyword", defaultValue = "", required = false) String keyword
    ) {
        return userService.getAllUsers(pageSize, pageNo, sortDir, sortField, keyword);
    }

    @GetMapping("/is_email_unique/{email}")
    public boolean check_email(@PathVariable String email) {
        return appUserRep.findByEmail(email).isPresent();
    }

    @PostMapping("/create_user")
    public AppUser createNewUser(@RequestBody CreateUpdateUser request) {
        return userService.createNewUser(request);
    }


    @GetMapping("/get_user/{userId}")
    public AppUser findUser(@PathVariable Long userId) {
        return userService.findUser(userId);
    }

    @DeleteMapping("/delete_user/{userId}")
    public void deleteUser(@PathVariable long userId) {
        userService.deleteUser(userId);
    }

    //update general
    @PutMapping("/update_user/{userId}")
    public AppUser updateUser(@RequestBody CreateUpdateUser request, @PathVariable Long userId) {
        return userService.updateUser(userId, request);
    }

    //Update Enabled-disabled
    @PutMapping("/user_enabled_disabled/{userId}")
    public void updateEnableSt(@PathVariable long userId) {
        userService.updateEnableStatus(userId);
    }

    @GetMapping("/get_roles")
    public List<Role> getRoles() {
        return userService.findAllRoles();
    }

    //Export Users info as CSV Format
    @GetMapping("/export_csv")
    public void exportToCsv(HttpServletResponse response) throws IOException {
        List<AppUser> users = userService.listAllUsers();
        UserCsvExporter exporter = new UserCsvExporter();
        exporter.export(users, response);
    }

    //Export Users info as CSV Format
    @GetMapping("/export_excel")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        List<AppUser> users = userService.listAllUsers();
        UserExcelExporter exporter = new UserExcelExporter();
        exporter.export(users, response);
    }

    //Export Users info as CSV Format
    @GetMapping("/export_pdf")
    public void exportToPdf(HttpServletResponse response) throws IOException {
        List<AppUser> users = userService.listAllUsers();
        UserPdfExporter exporter = new UserPdfExporter();
        exporter.export(users, response);
    }
}
