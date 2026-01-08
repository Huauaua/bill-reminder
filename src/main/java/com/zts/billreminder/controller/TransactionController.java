package com.zts.billreminder.controller;

import com.zts.billreminder.entity.Category;
import com.zts.billreminder.entity.TransactionRecord;
import com.zts.billreminder.service.CategoryService;
import com.zts.billreminder.service.TransactionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * 交易记录控制器
 */
@Controller
@RequestMapping("/transaction")
public class TransactionController {
    
    private final TransactionService transactionService;
    private final CategoryService categoryService;
    
    public TransactionController(TransactionService transactionService, CategoryService categoryService) {
        this.transactionService = transactionService;
        this.categoryService = categoryService;
    }
    
    /**
     * 记录列表页面
     */
    @GetMapping("/list")
    public String list(@RequestParam(required = false) Integer type, Model model) {
        List<TransactionRecord> records;
        if (type != null) {
            records = transactionService.findByType(type);
        } else {
            records = transactionService.findAll();
        }
        model.addAttribute("records", records);
        model.addAttribute("currentType", type);
        return "transaction/list";
    }
    
    /**
     * 新增记录页面
     */
    @GetMapping("/add")
    public String addPage(@RequestParam(defaultValue = "2") Integer type, Model model) {
        model.addAttribute("type", type);
        model.addAttribute("incomeCategories", categoryService.findIncomeCategories());
        model.addAttribute("expenseCategories", categoryService.findExpenseCategories());
        model.addAttribute("record", new TransactionRecord());
        return "transaction/form";
    }
    
    /**
     * 编辑记录页面
     */
    @GetMapping("/edit/{id}")
    public String editPage(@PathVariable Long id, Model model) {
        TransactionRecord record = transactionService.findById(id);
        if (record == null) {
            return "redirect:/transaction/list";
        }
        model.addAttribute("type", record.getType());
        model.addAttribute("incomeCategories", categoryService.findIncomeCategories());
        model.addAttribute("expenseCategories", categoryService.findExpenseCategories());
        model.addAttribute("record", record);
        return "transaction/form";
    }
    
    /**
     * 保存记录
     */
    @PostMapping("/save")
    public String save(TransactionRecord record, RedirectAttributes redirectAttributes) {
        try {
            // 根据分类设置类型
            Category category = categoryService.findById(record.getCategoryId());
            if (category != null) {
                record.setType(category.getType());
            }
            
            if (record.getId() == null) {
                transactionService.save(record);
                redirectAttributes.addFlashAttribute("message", "添加成功！");
            } else {
                transactionService.update(record);
                redirectAttributes.addFlashAttribute("message", "更新成功！");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "操作失败：" + e.getMessage());
        }
        return "redirect:/transaction/list";
    }
    
    /**
     * 删除记录
     */
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            transactionService.deleteById(id);
            redirectAttributes.addFlashAttribute("message", "删除成功！");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "删除失败：" + e.getMessage());
        }
        return "redirect:/transaction/list";
    }
    
    /**
     * 批量删除记录
     */
    @PostMapping("/delete-batch")
    @ResponseBody
    public Map<String, Object> deleteBatch(@RequestBody List<Long> ids, RedirectAttributes redirectAttributes) {
        try {
            int count = transactionService.deleteBatch(ids);
            return Map.of("success", true, "message", "成功删除" + count + "条记录", "deletedCount", count);
        } catch (Exception e) {
            return Map.of("success", false, "message", "批量删除失败：" + e.getMessage());
        }
    }
    
    /**
     * 批量更新记录
     */
    @PostMapping("/update-batch")
    @ResponseBody
    public Map<String, Object> updateBatch(@RequestBody List<TransactionRecord> records, RedirectAttributes redirectAttributes) {
        try {
            int[] results = transactionService.updateBatch(records);
            int updatedCount = 0;
            for (int result : results) {
                if (result > 0) updatedCount++;
            }
            return Map.of("success", true, "message", "成功更新" + updatedCount + "条记录", "updatedCount", updatedCount);
        } catch (Exception e) {
            return Map.of("success", false, "message", "批量更新失败：" + e.getMessage());
        }
    }
}