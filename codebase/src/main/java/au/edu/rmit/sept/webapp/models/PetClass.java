package au.edu.rmit.sept.webapp.models;

import jakarta.servlet.http.HttpSession;

import java.util.Optional;


public class PetClass {

        private Integer petId;
        private Integer userId;
        private String name;
        private String gender;
        private String species;
        private String breed;
        private String DOB;


        public PetClass() {
                petId = 0;
                userId = 0;
                name = "";
                gender = "";
                species = "";
                breed = "";
                DOB = "";
        }

        public PetClass(
                Integer petId,
                Integer userId,
                String name,
                String gender,
                String species,
                String breed,
                String DOB
        ) {
                this.petId = petId;
                this.userId = userId;
                this.name = name;
                this.gender = gender;
                this.species = species;
                this.breed = breed;
                this.DOB = DOB;
        }

        public Integer getPetId() { return petId; }

        public void setPetId(Integer petId) { this.petId = petId; }

        public Integer getUserId() {
                return userId;
        }

        public void setUserId(Integer userId) {
                this.userId = userId;
        }

        public String getName() {
                return name;
        }

        public void setName(String name) {
                this.name = name;
        }

        public String getGender() {
                return gender;
        }

        public void setGender(String gender) {
                this.gender = gender;
        }

        public String getSpecies() {
                return species;
        }

        public void setSpecies(String species) {
                this.species = species;
        }

        public String getBreed() {
                return breed;
        }

        public void setBreed(String breed) {
                this.breed = breed;
        }

        public String getDOB() {
                return DOB;
        }

        public void setDOB(String DOB) {
                this.DOB = DOB;
        }
        public String toInsertSQL(){
                return("INSERT INTO petData" +
                        "(userId," +
                        "petName," +
                        "gender," +
                        "species," +
                        "breed," +
                        "dateOfBirth) " +
                        "VALUES (" +
                        this.toString()+");");
        }

        public String toString(){
                return(String.format(
                        "%s, '%s', '%s', '%s', '%s', '%s'",
                        this.userId,
                        this.name,
                        this.gender,
                        this.species,
                        this.breed,
                        this.DOB
                ));
        }
}
