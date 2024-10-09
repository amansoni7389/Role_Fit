package com.app.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.entity.JobRole;
import com.app.entity.Skill;
import com.app.repository.JobRoleRepository;
import com.app.repository.SkillRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class JobRoleService {

    @Autowired
    private JobRoleRepository jobRoleRepository;

    @Autowired
    private SkillRepository skillRepository;

    // 1. Create a new job role
    public JobRole createJobRole(String name, List<String> skillNames) {
        // Fetching skills by their names from the database
        List<Skill> requiredSkills = skillRepository.findByNameIn(skillNames);

        // Creating a new JobRole object with the given name and the fetched skills
        JobRole jobRole = new JobRole();
        jobRole.setName(name);  // Setting the name of the job role
        jobRole.setRequiredSkills(requiredSkills);  // Setting the required skills

        // Saving the job role object to the database
        return jobRoleRepository.save(jobRole);
    }


    // 2. Get all job roles
    public List<JobRole> getAllJobRoles() {
        return jobRoleRepository.findAll();
    }

    // 3. Get job role by ID
    public Optional<JobRole> getJobRoleById(Long id) {
        return jobRoleRepository.findById(id);
    }

    // 4. Update a job role
    public JobRole updateJobRole(Long id, String name, List<String> skillNames) {
        Optional<JobRole> existingJobRole = jobRoleRepository.findById(id);
        if (existingJobRole.isPresent()) {
            JobRole jobRole = existingJobRole.get();
            List<Skill> updatedSkills = skillRepository.findByNameIn(skillNames);
            jobRole.setName(name);
            jobRole.setRequiredSkills(updatedSkills);
            return jobRoleRepository.save(jobRole);
        } else {
            throw new EntityNotFoundException("JobRole not found with id: " + id);
        }
    }

    // 5. Delete a job role by ID
    public void deleteJobRole(Long id) {
        jobRoleRepository.deleteById(id);
    }

    // 6. Recommend job roles based on user skills
    public List<JobRole> recommendJobRoles(List<String> skillNames) {
        List<Skill> userSkills = skillRepository.findByNameIn(skillNames);
        List<JobRole> allJobRoles = jobRoleRepository.findAll();

        return allJobRoles.stream()
            .filter(jobRole -> userSkills.containsAll(jobRole.getRequiredSkills()))
            .collect(Collectors.toList());
    }
}


