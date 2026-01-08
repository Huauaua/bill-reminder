package com.zts.billreminder.repository;

import com.zts.billreminder.entity.TransactionRecord;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 交易记录数据访问层
 */
@Repository
public class TransactionRepository {
    
    private final JdbcTemplate jdbcTemplate;
    
    public TransactionRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    /**
     * 查询所有记录（关联分类信息）
     */
    public List<TransactionRecord> findAll() {
        String sql = "SELECT t.id, t.category_id, t.type, t.amount, t.remark, t.transaction_date, " +
                "t.create_time, t.update_time, c.name as category_name, c.icon as category_icon " +
                "FROM transaction_record t " +
                "LEFT JOIN category c ON t.category_id = c.id " +
                "ORDER BY t.transaction_date DESC, t.id DESC";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(TransactionRecord.class));
    }
    
    /**
     * 根据类型查询记录
     */
    public List<TransactionRecord> findByType(Integer type) {
        String sql = "SELECT t.id, t.category_id, t.type, t.amount, t.remark, t.transaction_date, " +
                "t.create_time, t.update_time, c.name as category_name, c.icon as category_icon " +
                "FROM transaction_record t " +
                "LEFT JOIN category c ON t.category_id = c.id " +
                "WHERE t.type = ? " +
                "ORDER BY t.transaction_date DESC, t.id DESC";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(TransactionRecord.class), type);
    }
    
    /**
     * 根据日期范围查询记录
     */
    public List<TransactionRecord> findByDateRange(LocalDate startDate, LocalDate endDate) {
        String sql = "SELECT t.id, t.category_id, t.type, t.amount, t.remark, t.transaction_date, " +
                "t.create_time, t.update_time, c.name as category_name, c.icon as category_icon " +
                "FROM transaction_record t " +
                "LEFT JOIN category c ON t.category_id = c.id " +
                "WHERE t.transaction_date BETWEEN ? AND ? " +
                "ORDER BY t.transaction_date DESC, t.id DESC";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(TransactionRecord.class), startDate, endDate);
    }
    
    /**
     * 根据ID查询记录
     */
    public TransactionRecord findById(Long id) {
        String sql = "SELECT t.id, t.category_id, t.type, t.amount, t.remark, t.transaction_date, " +
                "t.create_time, t.update_time, c.name as category_name, c.icon as category_icon " +
                "FROM transaction_record t " +
                "LEFT JOIN category c ON t.category_id = c.id " +
                "WHERE t.id = ?";
        List<TransactionRecord> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(TransactionRecord.class), id);
        return list.isEmpty() ? null : list.get(0);
    }
    
    /**
     * 新增记录
     */
    public Long save(TransactionRecord record) {
        String sql = "INSERT INTO transaction_record (category_id, type, amount, remark, transaction_date) VALUES (?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, record.getCategoryId());
            ps.setInt(2, record.getType());
            ps.setBigDecimal(3, record.getAmount());
            ps.setString(4, record.getRemark());
            ps.setDate(5, Date.valueOf(record.getTransactionDate()));
            return ps;
        }, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }
    
    /**
     * 更新记录
     */
    public int update(TransactionRecord record) {
        String sql = "UPDATE transaction_record SET category_id = ?, type = ?, amount = ?, remark = ?, transaction_date = ? WHERE id = ?";
        return jdbcTemplate.update(sql, record.getCategoryId(), record.getType(), 
                record.getAmount(), record.getRemark(), record.getTransactionDate(), record.getId());
    }
    
    /**
     * 删除记录
     */
    public int deleteById(Long id) {
        String sql = "DELETE FROM transaction_record WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }
    
    /**
     * 批量删除记录
     */
    public int deleteBatch(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return 0;
        }
        String sql = "DELETE FROM transaction_record WHERE id IN (" + 
                    String.join(",", ids.stream().map(id -> "?").toArray(String[]::new)) + ")";
        return jdbcTemplate.update(sql, ids.toArray());
    }
    
    /**
     * 批量更新记录
     */
    public int[] updateBatch(List<TransactionRecord> records) {
        if (records == null || records.isEmpty()) {
            return new int[0];
        }
        
        String sql = "UPDATE transaction_record SET category_id = ?, type = ?, amount = ?, remark = ?, transaction_date = ? WHERE id = ?";
        List<Object[]> batchArgs = records.stream()
            .map(record -> new Object[]{
                record.getCategoryId(),
                record.getType(),
                record.getAmount(),
                record.getRemark(),
                record.getTransactionDate(),
                record.getId()
            })
            .collect(Collectors.toList()); // 使用 collect(Collectors.toList()) 替代 toList()
            
        return jdbcTemplate.batchUpdate(sql, batchArgs);
    }
    
    /**
     * 统计总收入
     */
    public BigDecimal sumIncome() {
        String sql = "SELECT COALESCE(SUM(amount), 0) FROM transaction_record WHERE type = 1";
        return jdbcTemplate.queryForObject(sql, BigDecimal.class);
    }
    
    /**
     * 统计总支出
     */
    public BigDecimal sumExpense() {
        String sql = "SELECT COALESCE(SUM(amount), 0) FROM transaction_record WHERE type = 2";
        return jdbcTemplate.queryForObject(sql, BigDecimal.class);
    }
    
    /**
     * 按月统计收支
     */
    public BigDecimal sumByTypeAndMonth(Integer type, int year, int month) {
        String sql = "SELECT COALESCE(SUM(amount), 0) FROM transaction_record WHERE type = ? AND YEAR(transaction_date) = ? AND MONTH(transaction_date) = ?";
        return jdbcTemplate.queryForObject(sql, BigDecimal.class, type, year, month);
    }
    
    /**
     * 获取最近N天的每日收支统计
     */
    public List<Map<String, Object>> getDailyStats(int days) {
        String sql = "SELECT DATE(transaction_date) as date, " +
                "SUM(CASE WHEN type = 1 THEN amount ELSE 0 END) as income, " +
                "SUM(CASE WHEN type = 2 THEN amount ELSE 0 END) as expense " +
                "FROM transaction_record " +
                "WHERE transaction_date >= DATE_SUB(CURDATE(), INTERVAL ? DAY) " +
                "GROUP BY DATE(transaction_date) " +
                "ORDER BY DATE(transaction_date)";
        return jdbcTemplate.queryForList(sql, days);
    }
}