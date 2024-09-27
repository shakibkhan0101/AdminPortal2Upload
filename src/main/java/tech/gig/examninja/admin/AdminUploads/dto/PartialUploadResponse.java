package tech.gig.examninja.admin.AdminUploads.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PartialUploadResponse extends ApiResponse{
    private String status;
    private String message;
    private List<String> failedQuestions;
}
