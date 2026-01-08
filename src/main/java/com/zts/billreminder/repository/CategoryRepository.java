package com.zts.billreminder.repository;

import com.zts.billreminder.entity.Category;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 分类数据访问层
 */
@Repository
public class CategoryRepository {
    
    private final JdbcTemplate jdbcTemplate;
    
    public CategoryRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    /**
     * 查询所有分类
     */
    public List<Category> findAll() {
        String sql = "SELECT id, name, type, icon, create_time, update_time FROM category ORDER BY type, id";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Category.class));
    }
    
    /**
     * 根据类型查询分类
     * @param type 1-收入，2-支出
     */
    public List<Category> findByType(Integer type) {
        String sql = "SELECT id, name, type, icon, create_time, update_time FROM category WHERE type = ? ORDER BY id";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Category.class), type);
    }
    
    /**
     * 根据ID查询分类
     */
    public Category findById(Long id) {
        String sql = "SELECT id, name, type, icon, create_time, update_time FROM category WHERE id = ?";
        List<Category> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Category.class), id);
        return list.isEmpty() ? null : list.get(0);
    }
}
