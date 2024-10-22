package com.example.demo.dbinitializer;

import com.example.demo.domain.model.AnswerOption;
import com.example.demo.domain.model.Question;
import com.example.demo.domain.model.Student;
import com.example.demo.domain.model.StudentAnswer;
import com.example.demo.domain.model.StudentTest;
import com.example.demo.domain.model.Test;
import com.example.demo.domain.model.TestProgressState;
import com.example.demo.domain.model.TestState;
import com.example.demo.repository.StudentAnswerRepository;
import com.example.demo.repository.StudentRepository;
import com.example.demo.repository.StudentTestRepository;
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
    private final StudentTestRepository studentTestRepository;


    @Override
    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @Transactional
    public void run(String... args) {
        var firstTest = createGeneralCultureTest();
        var secondTest = createMathTest();
        testRepository.saveAll(List.of(firstTest, secondTest));

        var cagatay = new Student(260201077L, "cagatay", "iba", "cagatay.iba@gmail.com");
        var ali = new Student(362201088L, "ali", "demir", "ali.demir@gmail.com");
        studentRepository.saveAll(List.of(cagatay, ali));

        // cagatay solved first test and started second currently not submitted
        var firstTestQuestions = firstTest.getQuestions();
        var firstTestFirstQuestion = firstTestQuestions.getFirst();
        var firstTestSecondQuestion = firstTestQuestions.get(1);
        var secondTestQuestions = secondTest.getQuestions();
        var secondTestFirstQuestion = secondTestQuestions.getFirst();
        studentAnswerRepository.saveAll(List.of(
                new StudentAnswer(
                        cagatay,
                        firstTestFirstQuestion
                            .getAnswerOptions().stream()
                            .filter(answerOption -> !answerOption.getIsCorrectOption())
                            .findAny()
                            .get(),
                        firstTestFirstQuestion
                ), // answers first question wrong (first test)
                new StudentAnswer(
                        cagatay,
                        firstTestSecondQuestion
                            .getAnswerOptions().stream()
                            .filter(AnswerOption::getIsCorrectOption)
                            .findAny()
                            .get(),
                        firstTestSecondQuestion
                ), // answers second question correct (first test)
                new StudentAnswer(
                        cagatay,
                        secondTestFirstQuestion
                            .getAnswerOptions().stream()
                            .filter(AnswerOption::getIsCorrectOption)
                            .findAny()
                            .get(),
                        secondTestFirstQuestion
                ) // answers first question correct (second test)
        ));
        var cagatayFirstTestProgress = new StudentTest(TestProgressState.SUBMITTED, cagatay, firstTest);
        var cagataySecondTestProgress = new StudentTest(TestProgressState.STARTED, cagatay, secondTest);
        studentTestRepository.saveAll(List.of(cagatayFirstTestProgress, cagataySecondTestProgress));

        // ali starts first tests answers one question and leaves
        studentAnswerRepository.saveAll(List.of(
                new StudentAnswer(
                        ali,
                        firstTestFirstQuestion
                            .getAnswerOptions().stream()
                            .filter(AnswerOption::getIsCorrectOption)
                            .findAny()
                            .get(),
                        firstTestFirstQuestion
                ) // answers first question wrong (first test)
        ));
        var aliFirstTestProgress = new StudentTest(TestProgressState.STARTED, ali, firstTest);
        studentTestRepository.saveAll(List.of(aliFirstTestProgress));
    }

    private Test createGeneralCultureTest() {
        var questionOneOptions = List.of(
                createAnswerOption('A', "Istanbul", false),
                createAnswerOption('B', "Ankara", true),
                createAnswerOption('C', "Erzurum", false),
                createAnswerOption('D', "Konya", false)
        );
        var questionOne = createQuestion("What is the capital of Turkey?", 1, questionOneOptions);

        var questionTwoOptions = List.of(
                createAnswerOption('A', "1922", false),
                createAnswerOption('B', "2000", false),
                createAnswerOption('C', "1920", false),
                createAnswerOption('D', "1923", true)
        );
        var questionTwo = createQuestion("In which year was the Republic of Turkey founded?", 2, questionTwoOptions);

        var testQuestions = List.of(questionOne, questionTwo);
        return createTest("general culture", TestState.READY, testQuestions);
    }

    private Test createMathTest() {
        var questionOneOptions = List.of(
                createAnswerOption('A', "3", false),
                createAnswerOption('B', "4", false),
                createAnswerOption('C', "2", true),
                createAnswerOption('D', "1", false)
        );
        var questionOne = createQuestion("What is the square root of 4?", 1, questionOneOptions);

        var questionTwoOptions = List.of(
                createAnswerOption('A', "4", true),
                createAnswerOption('B', "5", false),
                createAnswerOption('C', "6", false),
                createAnswerOption('D', "7", false)
        );
        var questionTwo = createQuestion("What is 2 + 2?", 2, questionTwoOptions);

        var testQuestions = List.of(questionOne, questionTwo);
        return createTest("math", TestState.READY, testQuestions);
    }

    private Test createTest(String name, TestState state, List<Question> questions) {
        var test = new Test(name, state, new ArrayList<>());
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
