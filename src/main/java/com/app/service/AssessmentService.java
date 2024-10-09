package com.app.service;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.entity.AssessmentResult;
import com.app.entity.JobRole;
import com.app.entity.Question;
import com.app.entity.Skill;
import com.app.entity.User;
import com.app.entity.UserResponse;
import com.app.repository.AssessmentResultRepository;
import com.app.repository.JobRoleRepository;
import com.app.repository.QuestionRepository;
import com.app.repository.SkillRepository;
import com.app.repository.UserRepository;
import com.app.repository.UserResponseRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class AssessmentService {

    @Autowired
    private QuestionRepository questionRepository;
    
    @Autowired
    private SkillRepository skillRepository;
    @Autowired
    private AssessmentResultRepository assessmentResultRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JobRoleRepository jobRoleRepository;
    
    @Autowired
    private UserResponseRepository userResponseRepository;

    // Method to get questions for a specific job role
    public List<Question> getAssessmentQuestions(List<String> skillNames) {
        List<Skill> skills = skillRepository.findByNameIn(skillNames);
        List<Question> allQuestions = new ArrayList<>();

        for (Skill skill : skills) {
            // Fetching 5 random questions for each skill
            List<Question> questions = questionRepository.findTop5RandomQuestionsBySkill(skill.getId());
            allQuestions.addAll(questions);
        }

        // Shuffle the questions to ensure random order
        Collections.shuffle(allQuestions);
        return allQuestions.stream().limit(20).collect(Collectors.toList()); // Limit to 20 questions
    }
    // Method to calculate the score based on user responses
    public int submitAssessment(Long userId, Long jobRoleId, List<UserResponse> userResponses) {
        // Fetch user and job role
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        JobRole jobRole = jobRoleRepository.findById(jobRoleId)
                .orElseThrow(() -> new EntityNotFoundException("Job Role not found"));

        int score = 0;

        // Loop through each user response
        for (UserResponse response : userResponses) {
            if (response.getQuestion() == null || response.getQuestion().getId() == null) {
                throw new IllegalArgumentException("Question ID in user response must not be null");
            }

            // Fetch the question from the repository
            Optional<Question> questionOpt = questionRepository.findById(response.getQuestion().getId());

            if (questionOpt.isPresent()) {
                Question question = questionOpt.get();
                boolean isCorrect = question.getCorrectAnswer().equalsIgnoreCase(response.getSelectedOption());

                // Increment score if the selected option is correct
                if (isCorrect) {
                    score++;
                }

                // Save the user response to the database
                response.setUser(user);
                response.setJobRole(jobRole);
                response.setQuestion(question);
                response.setCorrect(isCorrect);
                userResponseRepository.save(response);
            } else {
                throw new EntityNotFoundException("Question not found for ID: " + response.getQuestion().getId());
            }
        }

        // Save the total score into the AssessmentResult table
        saveAssessmentResult(userId, jobRoleId, score);

        return score;
    }
     
    public AssessmentResult saveAssessmentResult(Long userId, Long jobRoleId, int totalScore) {
        // Fetch user and job role
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        JobRole jobRole = jobRoleRepository.findById(jobRoleId)
                .orElseThrow(() -> new EntityNotFoundException("Job Role not found"));

        // Create a new AssessmentResult
        AssessmentResult assessmentResult = new AssessmentResult();
        assessmentResult.setUser(user);
        assessmentResult.setJobRole(jobRole);
        assessmentResult.setTotalScore(totalScore);
        assessmentResult.setSubmittedAt(LocalDateTime.now());

        // Save the AssessmentResult to the database
        return assessmentResultRepository.save(assessmentResult);
    }



    
 // Method to fetch the result by userId and jobRoleId
    public AssessmentResult getAssessmentResult(Long userId, Long jobRoleId) {
        return assessmentResultRepository.findByUserIdAndJobRoleId(userId, jobRoleId);
    }
}

