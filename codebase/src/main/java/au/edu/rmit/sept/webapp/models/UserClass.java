package au.edu.rmit.sept.webapp.models;

import jakarta.servlet.http.HttpSession;

import java.util.Optional;

/**
 * Used to input new User's into DB.<br/>
 * As records are immutable, an editable object is needed for form submission of objects
 * @String firstName
 * @String lastName
 * @String password (Should be hashed)
 * @String confirmPassword
 * @String phoneNumber
 * @String email
 */
public class UserClass {

        private String firstName;
        private String lastName;
        private String password, confirmPassword;
        private String phoneNumber;
        private String email;

        // ONLY USE THIS VARIABLE TO STORE THE CURRENTLY LOGGED-IN USER
//        private static User currentUser;
//        private static HttpSession session;

        public UserClass() {
                firstName = "";
                lastName = "";
                password = "";
                confirmPassword = "";
                phoneNumber = "";
                email = "";
        }

        public UserClass(String firstName, String lastName, String password, String phoneNumber, String email) {
                this.firstName = firstName;
                this.lastName = lastName;
                this.password = password;
                this.phoneNumber = phoneNumber;
                this.email = email;
        }

        //Getters and Setters are here so that Thymeleaf can use them for Form detail filling
        public String getFirstName() { return firstName; }

        public void setFirstName(String firstName) { this.firstName = firstName; }

        public String getLastName() { return lastName; }

        public void setLastName(String lastName) { this.lastName = lastName; }

        public String getPassword() { return password; }

        public void setPassword(String password) { this.password = password; }

        public String getConfirmPassword() { return confirmPassword; }

        public void setConfirmPassword(String confirmPassword) { this.confirmPassword = confirmPassword; }

        public String getPhoneNumber() { return phoneNumber; }

        public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

        public String getEmail() { return email; }

        public void setEmail(String email) { this.email = email; }

        // Used to format class details into SQL insertion command
        // Permission Value is set to 'None' by default as it should be raised in an Admin panel
        public String toInsertSQL(){
                return("INSERT INTO userAccount " +
                        "(firstName," +
                        "lastName," +
                        "passwordHash," +
                        "phoneNumber," +
                        "emailAddress," +
                        "permissionLevel) " +
                        "VALUES (" +
                        this.toString()+"," +
                        " 'None');");
        }

        public String toString(){
                return(String.format(
                        "'%s', '%s', '%s', '%s', '%s'",
                        this.firstName,
                        this.lastName,
                        this.password,
                        this.phoneNumber,
                        this.email
                ));
        }

//        public static void setCurrentUser(User user) {
//                session.setAttribute("currentUser", user);
//        }
//
//        public static User getCurrentUser() {
//                return (User)session.getAttribute("currentUser");
//        }
}
