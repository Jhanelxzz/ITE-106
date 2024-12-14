# Payroll System

The **Payroll System** is a simple GUI-based application written in Java that allows users to manage employee payroll data. The system calculates the gross and net pay of employees based on their hourly rate and hours worked. The application uses **Swing** for the graphical user interface and **BufferedReader/Writer** for handling file operations. All payroll records are stored in a file named `payroll.txt`.

## Key Features

1. **Add Employee:**
   - Allows the user to input employee details such as ID, name, hourly rate, and hours worked. It then calculates the gross pay and net pay, and displays the information in a table.
   
2. **Calculate Pay:**
   - Calculates the gross and net pay of an employee by entering their Employee ID. Gross pay is calculated as `hourly rate * hours worked`, and net pay is calculated as 80% of the gross pay.

3. **Save Records:**
   - Saves all employee payroll records (including Employee ID, Name, Hourly Rate, Hours Worked, Gross Pay, and Net Pay) into a text file (`payroll.txt`).
   
4. **Display Records:**
   - Displays all saved payroll records in a table format. It loads the records from `payroll.txt` and shows them within the application.

5. **File Handling:**
   - Reads and writes employee payroll data to a text file (`payroll.txt`), ensuring data persistence even after the application is closed and reopened.

## How to Compile and Run the Application

1. Open **Command Prompt** or **Terminal** and navigate to the folder containing the `PayrollSystem.java` file:
   ```bash
   - cd C:\path\to\your\file
2. Compile the Java file:
   - `javac PhonebookSystem.java`
3. Run the compiled Java application:
   - `java PhonebookSystem`
### Notes:

   - Ensure the payroll.txt file exists in the same directory as the Java file. If the file does not exist, the application will create it automatically when saving or loading payroll records.
   - Payroll records are stored in payroll.txt in the following format:
    (employeeID,name,hourlyRate,hoursWorked,grossPay,netPay)
   - The system calculates net pay as 80% of gross pay for each employee.
