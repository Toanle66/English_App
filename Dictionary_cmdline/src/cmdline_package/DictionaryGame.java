package cmdline_package;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.Random;

public class DictionaryGame {

    //private static Map<String, Quiz> quizzes = new HashMap<>();

    static Quiz quizzes;
    static Scanner scanner = new Scanner(System.in);

    public static void play() {
        try {
            File ques = new File("src\\resources\\ques.txt");
            Scanner text = new Scanner(ques);
            quizzes = createQuiz(text);
            takeQuiz();
            text.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            //e.printStackTrace();
        }
    }

    private static Quiz createQuiz(Scanner text) {
        Quiz quiz = new Quiz();
        int numQuestions = Integer.parseInt(text.nextLine());
        for (int i = 0; i < numQuestions; i++) {
            String question = text.nextLine();
            int numChoices = Integer.parseInt(text.nextLine());
            List<String> choices = new ArrayList<>();
            for (int j = 0; j < numChoices; j++) {
                String choice = text.nextLine();
                choices.add(choice);
            }
            //System.out.println("Enter the index of the correct choice:");
            int correctChoice = Integer.parseInt(text.nextLine()) - 1;
            quiz.addQuestion(new Question(question, choices, correctChoice));
        }
        return quiz;
    }

    private static void takeQuiz() {
        if (quizzes == null) {
            System.out.println("Quiz not found.");
            return;
        }
        int score = 0;
        int min = 0;
        int max = quizzes.getNumQuestions() - 1;
        int count = max - min + 1;

        List<Integer> numbers = new ArrayList<>();
        for (int i = min; i <= max; i++) {
            numbers.add(i);
        }
        // Shuffle the list to randomize the order
        long seed = System.nanoTime();
        Collections.shuffle(numbers, new Random(seed));

        for (int i = 0; i < count; i=i+1) {
            System.out.println(count);
            int next = numbers.get(i);
            Question question = quizzes.getQuestion(next);
            System.out.println("Question " + (i + 1) + ": " + question.question());
            List<String> choices = question.choices();
            for (int j = 0; j < choices.size(); j++) {
                System.out.println((j + 1) + ": " + choices.get(j));
            }
            System.out.println("Nhap dap an cua ban (hoac go END de thoat):");
            String userAnswer = scanner.nextLine();
            if (userAnswer.equals("END")) {
                System.out.println("Dang thoat chuong trinh...");
                break;
            } else if (userAnswer.matches("[0-9]+")) {
                int userAnswer2 = Integer.parseInt(userAnswer) - 1;
                if (userAnswer2 == question.correctChoice()) {
                    System.out.println("Dap an chinh xac !");
                    score++;
                } else {
                    System.out.println("Dap an sai. Dap an dung la " + (question.correctChoice() + 1) + ".");
                }
            } else {
                System.out.println("Dau vao khong hop le !");
            }
        }
        System.out.println("So diem ban dat duoc la " + score + " tren tong so " + count + " cau hoi.");
        System.out.println("CONGRATULATIONS!!!");
    }

//    private static void viewQuiz(Scanner scanner) {
//        System.out.println("Enter the name of the quiz:");
//        String quizName = scanner.nextLine();
//        Quiz quiz = quizzes.get(quizName);
//        if (quiz == null) {
//            System.out.println("Quiz not found.");
//            return;}
//        System.out.println("Quiz: " + quiz.getName());
//        for (int i = 0; i < quiz.getNumQuestions(); i++) {
//            Question question = quiz.getQuestion(i);
//            System.out.println("Question " + (i+1) + ": " + question.getQuestion());
//            List<String> choices = question.getChoices();
//            for (int j = 0; j < choices.size(); j++) {
//                System.out.println((j+1) + ": " + choices.get(j));
//            }
//            System.out.println("Answer: " + (question.getCorrectChoice()+1));
//        }
//    }

//    private static void listQuizzes() {
//        System.out.println("Quizzes:");
//        for (String quizName : quizzes.keySet()) {
//            System.out.println("- " + quizName);
//        }
//    }


}

class Quiz {
    private final List<Question> questions = new ArrayList<>();

    public void addQuestion(Question question) {
        questions.add(question);
    }

    public Question getQuestion(int index) {
        return questions.get(index);
    }

    public int getNumQuestions() {
        return questions.size();
    }
}

record Question(String question, List<String> choices, int correctChoice) {

}
