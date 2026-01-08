package com.zts.billreminder.service;

import com.zts.billreminder.entity.TransactionRecord;
import com.zts.billreminder.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 交易记录服务层
 */
@Service
public class TransactionService {
    
    private final TransactionRepository transactionRepository;
    
    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }
    
    /**
     * 查询所有记录
     */
    public List<TransactionRecord> findAll() {
        return transactionRepository.findAll();
    }
    
    /**
     * 根据类型查询记录
     */
    public List<TransactionRecord> findByType(Integer type) {
        return transactionRepository.findByType(type);
    }
    
    /**
     * 根据日期范围查询记录
     */
    public List<TransactionRecord> findByDateRange(LocalDate startDate, LocalDate endDate) {
        return transactionRepository.findByDateRange(startDate, endDate);
    }
    
    /**
     * 根据ID查询记录
     */
    public TransactionRecord findById(Long id) {
        return transactionRepository.findById(id);
    }
    
    /**
     * 保存记录
     */
    public Long save(TransactionRecord record) {
        return transactionRepository.save(record);
    }
    
    /**
     * 更新记录
     */
    public int update(TransactionRecord record) {
        return transactionRepository.update(record);
    }
    
    /**
     * 删除记录
     */
    public int deleteById(Long id) {
        return transactionRepository.deleteById(id);
    }
    
    /**
     * 批量删除记录
     */
    public int deleteBatch(List<Long> ids) {
        return transactionRepository.deleteBatch(ids);
    }
    
    /**
     * 批量更新记录
     */
    public int[] updateBatch(List<TransactionRecord> records) {
        return transactionRepository.updateBatch(records);
    }
    
    /**
     * 获取收支统计数据
     */
    public Map<String, BigDecimal> getStatistics() {
        Map<String, BigDecimal> stats = new HashMap<>();
        BigDecimal totalIncome = transactionRepository.sumIncome();
        BigDecimal totalExpense = transactionRepository.sumExpense();
        stats.put("totalIncome", totalIncome);
        stats.put("totalExpense", totalExpense);
        stats.put("balance", totalIncome.subtract(totalExpense));
        return stats;
    }
    
    /**
     * 获取当月收支统计
     */
    public Map<String, BigDecimal> getMonthlyStatistics(int year, int month) {
        Map<String, BigDecimal> stats = new HashMap<>();
        BigDecimal monthIncome = transactionRepository.sumByTypeAndMonth(1, year, month);
        BigDecimal monthExpense = transactionRepository.sumByTypeAndMonth(2, year, month);
        stats.put("monthIncome", monthIncome);
        stats.put("monthExpense", monthExpense);
        stats.put("monthBalance", monthIncome.subtract(monthExpense));
        return stats;
    }
    
    /**
     * 获取最近N天的每日收支统计
     */
    public List<Map<String, Object>> getDailyStats(int days) {
        return transactionRepository.getDailyStats(days);
    }
}