package au.edu.rmit.sept.webapp.models;

public record Article(
        Integer articleID,
        String articleURL,
        String title,
        String imageURL,
        String content,
        String author,
        String publicationDate) {
}