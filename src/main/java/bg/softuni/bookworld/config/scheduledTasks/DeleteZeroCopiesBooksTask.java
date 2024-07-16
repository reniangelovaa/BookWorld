package bg.softuni.bookworld.config.scheduledTasks;

import bg.softuni.bookworld.data.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteZeroCopiesBooksTask {

    private final BookRepository bookRepository;

    @Scheduled(cron = "0 0 * * * *")
    public void deleteZeroCopiesBooks() {
        int deletedCount = bookRepository.deleteByCopiesEquals(0);

        System.out.println("Deleted " + deletedCount + " books with zero copies.");
    }
}
