package tech.gig.examninja.admin.AdminUploads.validation;

import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;
import tech.gig.examninja.admin.AdminUploads.exception.InvalidQuestionException;
import tech.gig.examninja.admin.AdminUploads.model.Question;

@Component
public class QuestionValidator {

    public Question validateAndCreateQuestion(CSVRecord record) {
        // Custom validations for each field
        String questionText = record.get("question");
        String questionType = record.get("questionType");
        String questionLevel = record.get("level");
        String answer = record.get("correct_answer");
        String description = record.get("answer_description");
        String options [] = {record.get("option1"),record.get("option2")};

        if (questionText == null || questionText.isEmpty()) {
            throw new InvalidQuestionException("Question text is missing");
        }
        if ((options[0] == null)&& (options[1] == null)|| (options[0].isEmpty())&&(options[1].isEmpty())) {
            throw new InvalidQuestionException("Question text is missing");
        }

        if (answer == null || answer.isEmpty()) {
            throw new InvalidQuestionException("Correct answer text is missing");
        }
        if (description == null || description.isEmpty()) {
            throw new InvalidQuestionException("Answer Description is missing");
        }
        if (questionType == null || (!questionType.equalsIgnoreCase("MCQ")
                && !questionType.equalsIgnoreCase("True/False"))) {
            throw new InvalidQuestionException("Question type must be defined. Allowed values are 'MCQ' or 'True/False'.");
        }
        if (questionLevel == null || questionLevel.isEmpty()) {
            throw new InvalidQuestionException("Question level must be defined");
        }

        // Create and populate Question entity
        Question question = new Question();
        question.setQuestion(questionText);
        question.setOption1(options[0]);
        question.setOption2(options[1]);
        question.setOption3(record.get("option3"));
        question.setOption4(record.get("option4"));
        question.setCorrectAnswer(answer);
        question.setAnswerDescription(description);
        question.setCategory(record.get("category"));
        question.setLevel(questionLevel);
        question.setQuestionType(questionType);

        return question;
    }
}
