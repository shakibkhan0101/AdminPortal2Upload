package tech.gig.examninja.admin.AdminUploads.contoller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import tech.gig.examninja.admin.AdminUploads.DTO.ApiResponse;
import tech.gig.examninja.admin.AdminUploads.exception.InvalidQuestionException;
import tech.gig.examninja.admin.AdminUploads.service.QuestionService;

import java.io.IOException;

@RestController
@RequestMapping("/api/admin/questions")
public class QuestionUploadController {
    @Autowired
    private QuestionService questionService;

//    @Autowired
//    public QuestionUploadController(QuestionService questionService) {
//        this.questionService = questionService;
//    }

    @PostMapping("/upload")
    public ResponseEntity<ApiResponse> uploadQuestions(
            @RequestParam("file") MultipartFile file,
            @RequestParam("examName") String examName) throws IOException {
        int questionCount = questionService.uploadQuestions(file, examName);
        return ResponseEntity.ok(new ApiResponse("Uploaded " + questionCount + " questions successfully", true));

    }
}
