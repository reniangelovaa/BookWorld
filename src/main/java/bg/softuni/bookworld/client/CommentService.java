package bg.softuni.bookworld.client;

import bg.softuni.bookworld.client.dto.CommentDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentsClient commentsClient;

    public void addComment(CommentDTO commentDto) {
        commentsClient.addComment(commentDto);
    }
}
