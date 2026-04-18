package com.codereview.service;

import com.codereview.model.CodeReview;
import com.codereview.repository.CodeReviewRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CodeReviewService {

    private final CodeReviewRepository repository;
    private final GeminiService geminiService;

    public CodeReviewService(CodeReviewRepository repository, GeminiService geminiService) {
        this.repository = repository;
        this.geminiService = geminiService;
    }

    public CodeReview reviewCode(String code, String language) {
        String review = geminiService.reviewCode(code, language);

        CodeReview entity = CodeReview.builder()
                .code(code)
                .language(language)
                .review(review)
                .rating(7) // (optional: extract from AI later)
                .build();

        return repository.save(entity);
    }

    public List<CodeReview> getAll() {
        return repository.findAll();
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public CodeReview getById(Long id) {
        return repository.findById(id).orElseThrow();
    }
}