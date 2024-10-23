package au.edu.rmit.sept.webapp.models;

public record User(
        Integer userId,
        String firstName,
        String lastName,
        String password,
        String phoneNumber,
        String email,
        String permissionLevel
        )
{
        public Integer userID() {return userId;}
}
