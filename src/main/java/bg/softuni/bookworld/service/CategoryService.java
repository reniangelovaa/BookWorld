package bg.softuni.bookworld.service;

import bg.softuni.bookworld.data.CategoryRepository;
import bg.softuni.bookworld.model.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public Category findById(Long id){
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isPresent()) {
            return optionalCategory.get();
        } else {
            throw new IllegalArgumentException("Category not found");
        }
    }
}
