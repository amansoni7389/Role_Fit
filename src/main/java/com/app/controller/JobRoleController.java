package com.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.entity.JobRole;
import com.app.entity.Skill;
import com.app.repository.SkillRepository;
import com.app.service.JobRoleService;

@RestController
@RequestMapping("/api/jobroles")
public class JobRoleController {

    @Autowired
    private JobRoleService jobRoleService;

    // 1. Create a new job role
    @PostMapping("/create")
    public ResponseEntity<JobRole> createJobRole(
        @RequestParam String name, @RequestBody List<String> skillNames) {
        JobRole jobRole = jobRoleService.createJobRole(name, skillNames);
        return ResponseEntity.ok(jobRole);
    }

    // 2. Get all job roles
    @GetMapping("/all")
    public ResponseEntity<List<JobRole>> getAllJobRoles() {
        List<JobRole> jobRoles = jobRoleService.getAllJobRoles();
        return ResponseEntity.ok(jobRoles);
    }

    // 3. Get a specific job role by ID
    @GetMapping("/{id}")
    public ResponseEntity<JobRole> getJobRoleById(@PathVariable Long id) {
        return jobRoleService.getJobRoleById(id)
            .map(jobRole -> ResponseEntity.ok(jobRole))
            .orElse(ResponseEntity.notFound().build());
    }

    // 4. Update an existing job role
    @PutMapping("/{id}")
    public ResponseEntity<JobRole> updateJobRole(
        @PathVariable Long id, @RequestParam String name, @RequestBody List<String> skillNames) {
        JobRole updatedJobRole = jobRoleService.updateJobRole(id, name, skillNames);
        return ResponseEntity.ok(updatedJobRole);
    }

    // 5. Delete a job role by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJobRole(@PathVariable Long id) {
        jobRoleService.deleteJobRole(id);
        return ResponseEntity.noContent().build();
    }

    // 6. Recommend job roles based on user skills
    @PostMapping("/recommend")
    public ResponseEntity<List<JobRole>> recommendJobRoles(@RequestBody List<String> skillNames) {
        List<JobRole> recommendedRoles = jobRoleService.recommendJobRoles(skillNames);
        return ResponseEntity.ok(recommendedRoles);
    }
}

