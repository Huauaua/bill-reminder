package com.zts.billreminder.service;

import com.zts.billreminder.entity.Category;
import com.zts.billreminder.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 分类服务层
 */
@Service
public class CategoryService {
    
    private final CategoryRepository categoryRepository;
    
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
    
    /**
     * 查询所有分类
     */
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }
    
    /**
     * 查询收入分类
     */
    public List<Category> findIncomeCategories() {
        return categoryRepository.findByType(1);
    }
    
    /**
     * 查询支出分类
     */
    public List<Category> findExpenseCategories() {
        return categoryRepository.findByType(2);
    }
    
    /**
     * 根据ID查询分类
     */
    public Category findById(Long id) {
        return categoryRepository.findById(id);
    }
}
