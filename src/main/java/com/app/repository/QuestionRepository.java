package com.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.entity.Question;


@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
	@Query(value = "SELECT * FROM questions WHERE skill_id = :skillId ORDER BY RAND() LIMIT 5", nativeQuery = true)
    List<Question> findTop5RandomQuestionsBySkill(@Param("skillId") Long skillId);
}
