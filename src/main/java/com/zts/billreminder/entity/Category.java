package com.zts.billreminder.entity;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * 收支分类实体类
 */
@Data
public class Category {
    
    /**
     * 分类ID
     */
    private Long id;
    
    /**
     * 分类名称
     */
    private String name;
    
    /**
     * 类型：1-收入，2-支出
     */
    private Integer type;
    
    /**
     * 图标
     */
    private String icon;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
