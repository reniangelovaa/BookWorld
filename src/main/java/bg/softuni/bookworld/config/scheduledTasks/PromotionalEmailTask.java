package bg.softuni.bookworld.config.scheduledTasks;

import bg.softuni.bookworld.data.BookRepository;
import bg.softuni.bookworld.data.UserRepository;
import bg.softuni.bookworld.model.Book;
import bg.softuni.bookworld.model.User;
import bg.softuni.bookworld.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PromotionalEmailTask {

    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final EmailService emailService;

    @Scheduled(cron = "0 0 10 * * ?")
    public void sendPromotionalEmails(){
        List<User> users = userRepository.findAll();
        List<Book> newBooks = bookRepository.findTop5ByOrderByReleaseDateDesc();
        StringBuilder emailContent = new StringBuilder("Check out our new books:\n");
        for (Book book : newBooks) {
            emailContent.append(book.getName())
                    .append(" by ")
                    .append(book.getAuthor().getFullName())
                    .append(" - $")
                    .append(book.getPrice())
                    .append("\n");
        }
        for (User user : users) {
            emailService.sendSimpleMessage(user.getEmail(), "New Books Available!", emailContent.toString());
        }
    }
}
