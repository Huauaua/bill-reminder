package com.zts.billreminder.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zts.billreminder.entity.TransactionRecord;
import com.zts.billreminder.service.TransactionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * 首页控制器
 */
@Controller
public class HomeController {
    
    private final TransactionService transactionService;
    private final ObjectMapper objectMapper;
    
    public HomeController(TransactionService transactionService, ObjectMapper objectMapper) {
        this.transactionService = transactionService;
        this.objectMapper = objectMapper;
    }
    
    /**
     * 首页 - 显示收支概览
     */
    @GetMapping("/")
    public String index(Model model) {
        // 获取统计数据
        Map<String, BigDecimal> stats = transactionService.getStatistics();
        model.addAttribute("totalIncome", stats.get("totalIncome"));
        model.addAttribute("totalExpense", stats.get("totalExpense"));
        model.addAttribute("balance", stats.get("balance"));
        
        // 获取当月统计
        LocalDate now = LocalDate.now();
        Map<String, BigDecimal> monthStats = transactionService.getMonthlyStatistics(now.getYear(), now.getMonthValue());
        model.addAttribute("monthIncome", monthStats.get("monthIncome"));
        model.addAttribute("monthExpense", monthStats.get("monthExpense"));
        model.addAttribute("monthBalance", monthStats.get("monthBalance"));
        
        // 获取最近30天的每日收支统计
        List<Map<String, Object>> dailyStats = transactionService.getDailyStats(30);
        try {
            model.addAttribute("dailyStatsJson", objectMapper.writeValueAsString(dailyStats));
        } catch (Exception e) {
            model.addAttribute("dailyStatsJson", "[]");
        }
        
        // 获取最近交易记录
        List<TransactionRecord> recentRecords = transactionService.findAll();
        if (recentRecords.size() > 10) {
            recentRecords = recentRecords.subList(0, 10);
        }
        model.addAttribute("recentRecords", recentRecords);
        
        return "index";
    }
}
