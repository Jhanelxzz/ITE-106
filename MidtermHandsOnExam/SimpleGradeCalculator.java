import java.util.Scanner;

public class SimpleGradeCalculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of students: ");
        int numStudents = scanner.nextInt();
        System.out.print("Enter the number of assignments: ");
        int numAssignments = scanner.nextInt();

        for (int i = 0; i < numStudents; i++) {
            System.out.print("Enter student's name: ");
            String name = scanner.next();
            double totalScore = 0;

            for (int j = 0; j < numAssignments; j++) {
                System.out.print("Enter score for assignment " + (j + 1) + ": ");
                double score = scanner.nextDouble();
                totalScore += score;
            }

            double averageScore = totalScore / numAssignments;
            String gradeLetter = calculateGrade(averageScore);
            System.out.println(name + "'s average score is: " + averageScore + " and the grade is: " + gradeLetter);
        }
    }

    public static String calculateGrade(double grade) {
        if (grade >= 90) {
            return "A";
        } else if (grade >= 80) {
            return "B";
        } else if (grade >= 70) {
            return "C";
        } else if (grade >= 60) {
            return "D";
        } else {
            return "F";
        }
    }
}9
