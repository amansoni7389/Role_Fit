package com.app.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class UserResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // Link the user taking the assessment

    @ManyToOne
    @JoinColumn(name = "jobrole_id", nullable = false)
    private JobRole jobRole; // Link the job role of the assessment

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    private Question question; // Link the question for the response
    
    private String selectedOption; // 'A', 'B', 'C', or 'D'
    private boolean isCorrect; // To track if the user's response was correct
}
