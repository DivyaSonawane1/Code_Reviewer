package com.codereview.controller;

import com.codereview.dto.ReviewDTO;
import com.codereview.model.CodeReview;
import com.codereview.service.CodeReviewService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@CrossOrigin
public class CodeReviewController {

    private final CodeReviewService service;

    public CodeReviewController(CodeReviewService service) {
        this.service = service;
    }

    @PostMapping
    public CodeReview review(@RequestBody ReviewDTO dto) {
        return service.reviewCode(dto.getCode(), dto.getLanguage());
    }

    @GetMapping
    public List<CodeReview> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public CodeReview getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}