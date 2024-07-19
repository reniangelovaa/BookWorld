package bg.softuni.bookworld.client;

import bg.softuni.bookworld.client.dto.CommentDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "comments-service", url = "http://localhost:8081")
public interface CommentsClient {

    @PostMapping("/api/comments")
    CommentDTO addComment(@RequestBody CommentDTO commentDTO);

    @DeleteMapping("/api/comments/{id}")
    void deleteComment(@PathVariable Long id);

    @GetMapping("/api/comments/book/{bookId}")
    List<CommentDTO> getCommentsByBookId(@PathVariable Long bookId);
}
