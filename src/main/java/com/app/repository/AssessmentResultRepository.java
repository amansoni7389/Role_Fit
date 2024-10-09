package com.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.entity.AssessmentResult;

@Repository
public interface AssessmentResultRepository extends JpaRepository<AssessmentResult, Long> {
	AssessmentResult findByUserIdAndJobRoleId(Long userId, Long jobRoleId);
}
