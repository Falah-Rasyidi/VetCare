package au.edu.rmit.sept.webapp.models;

public record Pet(
        Integer petId,
        Integer userId,
        String name,
        String gender,
        String species,
        String breed,
        String DOB
        )
{
        public Integer petId() {return petId;}
        public String petName() {return name;}
}
