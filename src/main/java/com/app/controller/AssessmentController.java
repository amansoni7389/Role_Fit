package com.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.app.entity.AssessmentResult;
import com.app.entity.Question;
import com.app.entity.UserResponse;
import com.app.service.AssessmentService;

@RestController
@RequestMapping("/api/assessments")
public class AssessmentController {

    @Autowired
    private AssessmentService assessmentService;

    // Endpoint to create assessment questions based on selected skills
    @PostMapping("/create")
    public ResponseEntity<List<Question>> createAssessment(@RequestBody List<String> skillNames) {
        try {
            if (skillNames == null || skillNames.isEmpty()) {
                return ResponseEntity.badRequest().body(null);
            }

            List<Question> questions = assessmentService.getAssessmentQuestions(skillNames);
            return ResponseEntity.ok(questions);
        } catch (Exception e) {
            // Log error and return internal server error
            System.err.println("Error creating assessment: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Endpoint for submitting user answers and calculating score
    @PostMapping("/submit")
    public ResponseEntity<Integer> submitAssessment(
            @RequestParam Long userId, 
            @RequestParam Long jobRoleId,
            @RequestBody List<UserResponse> userResponses) {
        
        int score = assessmentService.submitAssessment(userId, jobRoleId, userResponses);
        return ResponseEntity.ok(score);
    }

    // Endpoint to fetch the assessment result by userId and jobRoleId
    @GetMapping("/result")
    public ResponseEntity<AssessmentResult> getAssessmentResult(@RequestParam Long userId, 
                                                                @RequestParam Long jobRoleId) {
        try {
            if (userId == null || jobRoleId == null) {
                return ResponseEntity.badRequest().body(null);
            }

            AssessmentResult result = assessmentService.getAssessmentResult(userId, jobRoleId);
            if (result == null) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok(result);
        } catch (Exception e) {
            // Log any errors
            System.err.println("Error fetching assessment result: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
