import java.io.*;
import java.util.*;

public class StudentGradebook3 {
    public static void main(String[] args) {
        List<Student> students = new ArrayList<>();

        try (Scanner scanner = new Scanner(new File("student_data.txt"))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] data = line.split(",");

                if (data.length != 6) {
                    System.out.println("Invalid input format for line: " + line);
                    continue;
                }

                String name = formatName(data[0].trim());
                double[] scores = new double[5];

                try {
                    for (int i = 1; i < data.length; i++) {
                        scores[i - 1] = Double.parseDouble(data[i].trim());
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Error parsing scores for " + name + ": " + e.getMessage());
                    continue;
                }

                students.add(new Student(name, scores));
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: Input file not found.");
            return;
        }

        try (PrintWriter writer = new PrintWriter("student_grades.txt")) {
            writer.printf("%-20s %-5s %-5s %-10s %-5s %-5s %-10s %-10s%n", "Name", "Quiz1", "Quiz2", "Homework", "Midterm", "Final", "Average", "Grade");
            writer.println("---------------------------------------------------------------");

            for (Student student : students) {
                double average = student.calculateAverage();
                String grade = assignGrade(average);

                writer.printf("%-20s %-5s %-5s %-10s %-5s %-5s %-10.2f %-10s%n",
                        student.getName(), student.getScores()[0], student.getScores()[1],
                        student.getScores()[2], student.getScores()[3], student.getScores()[4],
                        average, grade);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: Output file could not be created.");
        }

        System.out.println("Student grades have been processed and written to student_grades.txt.");
    }

    private static String formatName(String name) {
        String[] parts = name.split(" ");
        if (parts.length != 2) {
            return name;
        }
        return parts[1] + ", " + parts[0];
    }

    private static String assignGrade(double average) {
        if (average >= 90) {
            return "A";
        } else if (average >= 80) {
            return "B";
        } else if (average >= 70) {
            return "C";
        } else if (average >= 60) {
            return "D";
        } else {
            return "F";
        }
    }
}

class Student {
    private String name;
    private double[] scores;

    public Student(String name, double[] scores) {
        this.name = name;
        this.scores = scores;
    }

    public String getName() {
        return name;
    }

    public double[] getScores() {
        return scores;
    }

    public double calculateAverage() {
        double sum = 0;
        for (double score : scores) {
            sum += score;
        }
        return sum / scores.length;
    }
}
