DROP TABLE IF EXISTS vetArticles;
CREATE TABLE vetArticles (
    articleId INT PRIMARY KEY AUTO_INCREMENT,
    articleURL VARCHAR(510) NOT NULL,
    title VARCHAR(255) NOT NULL,
    imageUrl VARCHAR(255),
    content TEXT NOT NULL,
    author VARCHAR(255) NOT NULL,
    publicationDate DATE NOT NULL
);