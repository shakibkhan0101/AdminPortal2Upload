package tech.gig.examninja.admin.AdminUploads.contoller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import tech.gig.examninja.admin.AdminUploads.dto.ApiResponse;
import tech.gig.examninja.admin.AdminUploads.exception.FileNotAttachedException;
import tech.gig.examninja.admin.AdminUploads.exception.InvalidFileFormatException;
import tech.gig.examninja.admin.AdminUploads.service.QuestionService;

import java.io.IOException;

@RestController
@RequestMapping("/api/admin/questions")
public class QuestionUploadController {
    @Autowired
    private QuestionService questionService;

    @PostMapping("/upload")
    public ResponseEntity<ApiResponse> uploadQuestions(
            @RequestParam("file") MultipartFile file,
            @RequestParam("examName") String examName) throws IOException {
        if (file.isEmpty()) {
            throw new FileNotAttachedException("File not Attached, Please upload a valid CSV file.");
        } else if(!file.getOriginalFilename().endsWith(".csv")) {
            throw new InvalidFileFormatException("Invalid file format. Only CSV files are allowed.");
        }
        ApiResponse response = questionService.uploadQuestions(file);
        return ResponseEntity.ok(response);
    }
}
