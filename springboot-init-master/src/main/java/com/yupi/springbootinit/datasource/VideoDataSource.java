package com.yupi.springbootinit.datasource;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Component;

/**
 * @Date: 2023/11/16 15:36
 * @Author: Lowell
 * @Description:
 **/
@Component
public class VideoDataSource implements DataSource{
    @Override
    public Page dosearch(String searchText, long pageNum, long pageSize) {
        return null;
    }
}
