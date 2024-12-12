### Explanation of the Application
This Phonebook System is a simple GUI-based application written in Java that allows users to manage a list of contacts. Users can add, delete, search, update, and display contacts, with all data stored in a file named phonebook.txt. The application uses Swing for creating the graphical user interface and BufferedReader/Writer for handling file operations.

### Key Features:
1. **Add Contact:**
   - Adds a new contact to the phonebook with a name and phone number.
2. **Delete Contact:**
   - Deletes a contact based on the name provided by the user.
3. **Search Contact:**
   - Searches for a contact by name and shows the details in a dialog box.
4. **Update Contact:**
   - Updates the phone number of an existing contact based on the name.
5. **Display Contacts:**
   - Displays all saved contacts in a text area within the application.

### How to Compile and Run the Application:
1. Open Command Prompt or Terminal and navigate to the folder containing the PhonebookSystem.java file:
   - `cd C:\path\to\your\file`
2. Compile the Java file:
   - `javac PhonebookSystem.java`
3. Run the compiled Java application:
   - `java PhonebookSystem`

### Notes:
   - Ensure the phonebook.txt file exists in the same directory as the Java file. The application will automatically create and modify this file when contacts are added, updated, or deleted.
   - Contacts are stored in the phonebook.txt file in the following format: `name,phoneNumber`.
