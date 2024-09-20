package tech.gig.examninja.admin.AdminUploads.service;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tech.gig.examninja.admin.AdminUploads.model.Question;
import tech.gig.examninja.admin.AdminUploads.repository.QuestionRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    public int uploadQuestions(MultipartFile file, String examName) throws IOException {
        int questionCount = 0;

        try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            Iterable<CSVRecord> records = CSVFormat.DEFAULT
                    .withHeader("question", "option1", "option2", "option3", "option4", "correct_answer", "answer_description", "category", "level")
                    .withSkipHeaderRecord()
                    .parse(reader);

            for (CSVRecord record : records) {
                Question question = new Question();
                question.setExamName(examName);
                question.setQuestion(record.get("question"));
                question.setOption1(record.get("option1"));
                question.setOption2(record.get("option2"));
                question.setOption3(record.get("option3"));
                question.setOption4(record.get("option4"));
                question.setCorrectAnswer(record.get("correct_answer"));
                question.setAnswerDescription(record.get("answer_description"));
                question.setCategory(record.get("category"));
                question.setLevel(record.get("level"));

                questionRepository.save(question);
                questionCount++;
            }
        }
        return questionCount;
    }
}
