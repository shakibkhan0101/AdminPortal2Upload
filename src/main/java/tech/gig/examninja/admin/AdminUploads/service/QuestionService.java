package tech.gig.examninja.admin.AdminUploads.service;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tech.gig.examninja.admin.AdminUploads.dto.ApiResponse;
import tech.gig.examninja.admin.AdminUploads.dto.PartialUploadResponse;
import tech.gig.examninja.admin.AdminUploads.exception.InvalidFileFormatException;
import tech.gig.examninja.admin.AdminUploads.model.Question;
import tech.gig.examninja.admin.AdminUploads.repository.QuestionRepository;
import tech.gig.examninja.admin.AdminUploads.validation.QuestionValidator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private QuestionValidator questionValidator;

    public ApiResponse uploadQuestions(MultipartFile file) throws IOException {
        int successCount = 0;
        int failedCount = 0;
        List<String> failedQuestions = new ArrayList<>();

        try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            Iterable<CSVRecord> records = CSVFormat.DEFAULT
                    .withHeader("question", "option1", "option2", "option3", "option4",
                            "correct_answer", "answer_description", "category", "level",
                            "questionType")
                    .withSkipHeaderRecord(true)
                    .parse(reader);
            // Check if the records are empty (i.e., no content in the file after the header)
            if (!records.iterator().hasNext()) {
                throw new InvalidFileFormatException("File content is empty. No records found.");
            }

            for (CSVRecord record : records) {
                // Validate and create the question object
                Question question = questionValidator.validateAndCreateQuestion(record);

                // Check for duplicates
                if (questionRepository.existsByQuestion(question.getQuestion())) {
                    failedCount++;
                    failedQuestions.add(" Duplicate question found : "+record.get("question"));
                    continue;
                }

                // Save the valid question
                questionRepository.save(question);
                successCount++;
            }
        }
        String inserted = successCount + " Questions uploaded successfully.";
        String denied="Questions failed "+failedCount;
        if (failedCount > 0) {

            return new PartialUploadResponse(inserted,denied,failedQuestions);
        }

        return new ApiResponse(inserted,denied);
    }
}
