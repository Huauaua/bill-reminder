package com.zts.billreminder.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 交易记录实体类
 */
@Data
public class TransactionRecord {
    
    /**
     * 记录ID
     */
    private Long id;
    
    /**
     * 分类ID
     */
    private Long categoryId;
    
    /**
     * 类型：1-收入，2-支出
     */
    private Integer type;
    
    /**
     * 金额
     */
    private BigDecimal amount;
    
    /**
     * 备注
     */
    private String remark;
    
    /**
     * 交易日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate transactionDate;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
    
    // 关联字段（非数据库字段）
    /**
     * 分类名称
     */
    private String categoryName;
    
    /**
     * 分类图标
     */
    private String categoryIcon;
}
