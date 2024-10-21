package com.example.demo.dbinitializer;

import com.example.demo.domain.AnswerOption;
import com.example.demo.domain.Question;
import com.example.demo.domain.Student;
import com.example.demo.domain.StudentAnswer;
import com.example.demo.domain.StudentTestProgress;
import com.example.demo.domain.Test;
import com.example.demo.domain.TestProgressState;
import com.example.demo.repository.StudentAnswerRepository;
import com.example.demo.repository.StudentRepository;
import com.example.demo.repository.StudentTestProgressRepository;
import com.example.demo.repository.TestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Component
public class DataProvider implements CommandLineRunner {

    private final TestRepository testRepository;
    private final StudentRepository studentRepository;
    private final StudentAnswerRepository studentAnswerRepository;
    private final StudentTestProgressRepository studentTestProgressRepository;


    @Override
    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @Transactional
    public void run(String... args) {
        var firstTest = createFirstTest();
        var secondTest = createSecondTest();
        testRepository.saveAll(List.of(firstTest, secondTest));

        var cagatay = new Student(260201077L, "cagatay", "iba", "cagatay.iba@gmail.com");
        var ali = new Student(362201088L, "ali", "demir", "ali.demir@gmail.com");
        studentRepository.saveAll(List.of(cagatay, ali));

        // cagatay solved first test and started second currently not submitted
        var firstTestQuestions = firstTest.getQuestions();
        var secondTestQuestions = secondTest.getQuestions();
        studentAnswerRepository.saveAll(List.of(
                new StudentAnswer(cagatay, firstTestQuestions
                        .getFirst()
                        .getAnswerOptions().stream()
                        .filter(answerOption -> !answerOption.getIsCorrectOption())
                        .findAny()
                        .get()
                ), // answers first question wrong (first test)
                new StudentAnswer(cagatay, firstTestQuestions
                        .get(1)
                        .getAnswerOptions().stream()
                        .filter(AnswerOption::getIsCorrectOption)
                        .findAny()
                        .get()
                ), // answers second question correct (first test)
                new StudentAnswer(cagatay, secondTestQuestions
                        .getFirst()
                        .getAnswerOptions().stream()
                        .filter(AnswerOption::getIsCorrectOption)
                        .findAny()
                        .get()
                ) // answers first question correct (second test)
        ));
        var cagatayFirstTestProgress = new StudentTestProgress(TestProgressState.SUBMITTED, cagatay, firstTest);
        var cagataySecondTestProgress = new StudentTestProgress(TestProgressState.STARTED, cagatay, secondTest);
        studentTestProgressRepository.saveAll(List.of(cagatayFirstTestProgress, cagataySecondTestProgress));

        // ali starts first tests answers one question and leaves
        studentAnswerRepository.saveAll(List.of(
                new StudentAnswer(ali, firstTestQuestions
                        .stream().filter(q -> q.getNumber().equals(1)).findAny().get()
                        .getAnswerOptions().stream()
                        .filter(AnswerOption::getIsCorrectOption)
                        .findAny()
                        .get()
                ) // answers first question wrong (first test)
        ));
        var aliFirstTestProgress = new StudentTestProgress(TestProgressState.STARTED, ali, firstTest);
        studentTestProgressRepository.saveAll(List.of(aliFirstTestProgress));
    }

    private Test createFirstTest() {
        var questionOneOptions = List.of(
                createAnswerOption('A', "Istanbul", false),
                createAnswerOption('B', "Ankara", true),
                createAnswerOption('C', "Erzurum", false),
                createAnswerOption('D', "Konya", false)
        );
        var questionOne = createQuestion("What is the capital of Turkey?", 1, questionOneOptions);

        var questionTwoOptions = List.of(
                createAnswerOption('A', "4", true),
                createAnswerOption('B', "5", false),
                createAnswerOption('C', "6", false),
                createAnswerOption('D', "7", false)
        );
        var questionTwo = createQuestion("What is 2 + 2?", 2, questionTwoOptions);

        var testQuestions = List.of(questionOne, questionTwo);
        return createTest("first demo test", testQuestions);
    }

    private Test createSecondTest() {
        var questionOneOptions = List.of(
                createAnswerOption('A', "3", false),
                createAnswerOption('B', "4", false),
                createAnswerOption('C', "2", true),
                createAnswerOption('D', "1", false)
        );
        var questionOne = createQuestion("What is the square root of 4?", 1, questionOneOptions);

        var questionTwoOptions = List.of(
                createAnswerOption('A', "1922", false),
                createAnswerOption('B', "2000", false),
                createAnswerOption('C', "1920", false),
                createAnswerOption('D', "1923", true)
        );
        var questionTwo = createQuestion("In which year was the Republic of Turkey founded?", 2, questionTwoOptions);

        var testQuestions = List.of(questionOne, questionTwo);
        return createTest("second demo test", testQuestions);
    }

    private Test createTest(String name, List<Question> questions) {
        var test = new Test(name, new ArrayList<>());
        questions.forEach(test::addQuestion);
        return test;
    }

    private Question createQuestion(String content, int questionNumber, List<AnswerOption> answerOptions) {
        var question = new Question();
        question.setContent(content);
        question.setNumber(questionNumber);
        question.setAnswerOptions(new ArrayList<>());
        answerOptions.forEach(question::addAnswerOption);
        return question;
    }

    private AnswerOption createAnswerOption(char label, String content, boolean correctOption) {
        var answerOption = new AnswerOption();
        answerOption.setLabel(label);
        answerOption.setContent(content);
        answerOption.setIsCorrectOption(correctOption);
        return answerOption;
    }
}
