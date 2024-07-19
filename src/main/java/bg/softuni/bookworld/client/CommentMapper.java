package bg.softuni.bookworld.client;

import bg.softuni.bookworld.client.dto.CommentDTO;

public class CommentMapper {
    public static CommentDTO toDto(Long bookId, String author, String text) {
        CommentDTO dto = new CommentDTO();
        dto.setBookId(bookId);
        dto.setAuthor(author);
        dto.setText(text);
        return dto;
    }
}
