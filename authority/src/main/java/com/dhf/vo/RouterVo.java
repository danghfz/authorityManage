package com.dhf.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author danghf
 * @version 1.0
 * @date 2023/09/10 16:49
 * 路由Vo
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RouterVo {
    /**
     * 路由地址
     */
    private String path;
    /**
     * 路由名称
     */
    private String name;
    /**
     * 路由组件
     */
    private String component;
    /**
     * 是否隐藏路由
     */
    private boolean alwaysShow;
    /**
     * 路由meta信息
     */
    private Meta meta;

    @Data
    @AllArgsConstructor
    public class Meta {
        /**
         * 路由标题
         */
        private String title;
        /**
         * 路由图标
         */
        private String icon;
        /**
         * 角色列表
         */
        private String[] roles;
    }

    /**
     * 子路由
     */
    private List<RouterVo> children = new ArrayList<>();
}
