package com.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.entity.Skill;
import com.app.entity.User;
import com.app.repository.SkillRepository;
import com.app.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SkillRepository skillRepository;

    // Create or update user with skills
    public User createUser(String name, List<String> skillNames) {
        // Find or create skills based on user input
        List<Skill> userSkills = skillRepository.findByNameIn(skillNames);
        User user = new User();
        user.setName(name);
        user.setSkills(userSkills);// if else exception
        return userRepository.save(user);
    }

    // Find user by ID
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    // Get all users (if needed)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}

