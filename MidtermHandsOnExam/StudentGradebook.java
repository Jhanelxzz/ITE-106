import java.util.Scanner;
import java.util.Arrays;

public class StudentGradebook {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Enter the number of students: ");
        int numStudents = scanner.nextInt();
        System.out.print("Enter the number of assignments: ");
        int numAssignments = scanner.nextInt();
        
        String[] names = new String[numStudents];
        double[][] grades = new double[numStudents][numAssignments];
        
        for (int i = 0; i < numStudents; i++) {
            System.out.print("Enter student's name: ");
            names[i] = scanner.next();
        }
        
        for (int i = 0; i < numStudents; i++) {
            System.out.println("Entering grades for " + names[i] + ":");
            for (int j = 0; j < numAssignments; j++) {
                System.out.print("Enter grade for assignment " + (j + 1) + ": ");
                grades[i][j] = scanner.nextDouble();
            }
        }
        
        double[] averageScores = new double[numStudents];
        char[] letterGrades = new char[numStudents];
        
        for (int i = 0; i < numStudents; i++) {
            double total = 0;
            for (int j = 0; j < numAssignments; j++) {
                total += grades[i][j];
            }
            averageScores[i] = total / numAssignments;

            if (averageScores[i] >= 90) {
                letterGrades[i] = 'A';
            } else if (averageScores[i] >= 80) {
                letterGrades[i] = 'B';
            } else if (averageScores[i] >= 70) {
                letterGrades[i] = 'C';
            } else if (averageScores[i] >= 60) {
                letterGrades[i] = 'D';
            } else {
                letterGrades[i] = 'F';
            }
        }
        
        System.out.println("\nStudent Gradebook:");
        System.out.printf("%-15s", "Name");
        for (int j = 1; j <= numAssignments; j++) {
            System.out.printf("%-10s", "A" + j);
        }
        System.out.printf("%-15s %-10s%n", "Average", "Grade");
        
        for (int i = 0; i < numStudents; i++) {
            System.out.printf("%-15s", names[i]);
            for (int j = 0; j < numAssignments; j++) {
                System.out.printf("%-10.2f", grades[i][j]);
            }
            System.out.printf("%-15.2f %-10c%n", averageScores[i], letterGrades[i]);
        }

        double highestAverage = Arrays.stream(averageScores).max().getAsDouble();
        double lowestAverage = Arrays.stream(averageScores).min().getAsDouble();
        
        System.out.println("\nHighest Average Score: " + highestAverage);
        System.out.println("Lowest Average Score: " + lowestAverage);
        
        for (int i = 0; i < numStudents - 1; i++) {
            for (int j = 0; j < numStudents - i - 1; j++) {
                if (averageScores[j] < averageScores[j + 1]) {
                    double tempAverage = averageScores[j];
                    averageScores[j] = averageScores[j + 1];
                    averageScores[j + 1] = tempAverage;

                    char tempLetter = letterGrades[j];
                    letterGrades[j] = letterGrades[j + 1];
                    letterGrades[j + 1] = tempLetter;

                    String tempName = names[j];
                    names[j] = names[j + 1];
                    names[j + 1] = tempName;

                    double[] tempGrades = grades[j];
                    grades[j] = grades[j + 1];
                    grades[j + 1] = tempGrades;
                }
            }
        }

        System.out.println("\nSorted Student Gradebook (by Average Score):");
        System.out.printf("%-15s", "Name");
        for (int j = 1; j <= numAssignments; j++) {
            System.out.printf("%-10s", "A" + j);
        }
        System.out.printf("%-15s %-10s%n", "Average", "Grade");

        for (int i = 0; i < numStudents; i++) {
            System.out.printf("%-15s", names[i]);
            for (int j = 0; j < numAssignments; j++) {
                System.out.printf("%-10.2f", grades[i][j]);
            }
            System.out.printf("%-15.2f %-10c%n", averageScores[i], letterGrades[i]);
        }

        scanner.close();
    }
}
