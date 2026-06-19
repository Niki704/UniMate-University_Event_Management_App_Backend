package com.unimate.controller;

import com.unimate.dto.AdminDTO;
import com.unimate.model.Admin;
import com.unimate.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "api/v1/")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/admins/get")
    public List<AdminDTO> getAllAdmins() {
        return adminService.getAllAdmins();
    }

//    @GetMapping("/getadmin/{Id}")
//    public AdminDTO getAdminById(@PathVariable int Id) {
//        return adminService.getAdminById(Id);
//    }

    @PostMapping("/admins/add")
    public ResponseEntity<String> saveAdmin(@RequestBody AdminDTO adminDTO) {
        adminService.saveAdmin(adminDTO);
        return ResponseEntity.status(201).body("Admin created successfully");
    }

    @PostMapping("/admins/bulk")
    public ResponseEntity<List<Admin>> addBulk(@RequestBody List<Admin> admins) {
        List<Admin> savedAdmins = adminService.addBulkAdmins(admins);
        return ResponseEntity.ok(savedAdmins);
    }

    @PutMapping("/admins/update/{id}")
    public ResponseEntity<String> updateAdmin(@PathVariable Integer id, @RequestBody AdminDTO adminDTO) {
        adminService.updateAdmin(id, adminDTO);
        return ResponseEntity.ok("Admin updated successfully");
    }

//    @DeleteMapping("/deleteadmin")
//    public String deleteAdmin(@RequestBody AdminDTO adminDTO) {
//        return adminService.deleteAdmin(adminDTO);
//    }

    @DeleteMapping("/admins/delete/{id}")
    public ResponseEntity<String> deleteAdminById(@PathVariable Integer id) {
        adminService.deleteAdminById(id);
        return ResponseEntity.ok("Admin deleted successfully");
    }
}
