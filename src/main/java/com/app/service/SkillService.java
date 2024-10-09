package com.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.entity.Skill;
import com.app.repository.SkillRepository;

@Service
public class SkillService {

    @Autowired
    private SkillRepository skillRepository;

    // Get all available skills
    public List<Skill> getAllSkills() {
        return skillRepository.findAll();
    }

    // Create a new skill
    public Skill createSkill(String skillName) {
        Skill skill = new Skill();
        skill.setName(skillName);
        return skillRepository.save(skill);
    }

    // Find skills by names (for recommendation logic)
    public List<Skill> getSkillsByNames(List<String> skillNames) {
        return skillRepository.findByNameIn(skillNames);
    }
}

