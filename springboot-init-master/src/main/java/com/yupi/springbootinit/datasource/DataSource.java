package com.yupi.springbootinit.datasource;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * @Date: 2023/11/16 12:37
 * @Author:Lowell
 * @Description:
 * 数据源接入（新接入的数据源必须实现）
 **/
public interface DataSource<T> {
    /**
     * 搜索
     * @param searchText
     * @param pageNum
     * @param pageSize
     * @return
     */
    Page<T> dosearch(String searchText, long pageNum, long pageSize);
}
