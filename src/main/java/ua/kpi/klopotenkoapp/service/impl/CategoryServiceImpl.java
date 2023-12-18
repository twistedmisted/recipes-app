package ua.kpi.klopotenkoapp.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ua.kpi.klopotenkoapp.repository.CategoryRepository;
import ua.kpi.klopotenkoapp.repository.projection.CategoryNameOnly;
import ua.kpi.klopotenkoapp.service.CategoryService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public List<String> getAllCategories() {
        log.debug("Getting all categories");
        List<CategoryNameOnly> categories = categoryRepository.findProjections();
        if (categories.isEmpty()) {
            log.warn("Cannot find categories");
            return new ArrayList<>();
        }
        return categories.stream()
                .map(CategoryNameOnly::getName)
                .toList();
    }
}
