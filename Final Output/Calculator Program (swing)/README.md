### Explanation of the Calculator Application
This Calculator application is a simple Java program with a graphical user interface (GUI) that allows users to perform basic arithmetic operations like addition, subtraction, multiplication, and division. The application uses Java's Swing library to create the interface, and it saves a history of calculations in a file called calculator_history.txt.

### Key Features:
1. **Basic Arithmetic Operations:**
   - Perform calculations for addition (+), subtraction (-), multiplication (*), and division (/).
2. **Clear Function (C):**
   - Clears the display and resets all values.
3. **Percent Function (%):**
   - Converts the displayed number into a percentage.
4. **Calculation History:**
   - Saves each calculation in a text file (calculator_history.txt) after pressing the = button.
5. **Error Handling:**
   - Displays "Error" if invalid operations like division by zero are attempted.

### How to Compile and Run the Application:
1. Open Command Prompt (Windows) or Terminal (Mac/Linux) and navigate to the directory where the Calculator.java file is saved.
2. Compile the Java file:
   - javac Calculator.java
3. Run the compiled Java application:
   - java Calculator

### Notes:
   - The calculator_history.txt file is used to store the history of calculations in the format: num1 operator num2 = result.
