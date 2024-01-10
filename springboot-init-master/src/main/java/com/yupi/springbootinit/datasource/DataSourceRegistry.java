package com.yupi.springbootinit.datasource;

import com.yupi.springbootinit.model.enums.SearchTypeEnum;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Date: 2023/11/16 15:11
 * @Author: Lowell
 * @Description:
 **/
@Component
public class DataSourceRegistry {
    @Resource
    private PostDataSource postDataSource;

    @Resource
    private PictureDataSource pictureDataSource;
    @Resource
    private UserDataSource userDataSource;

    private Map<String, DataSource<T>> typeDataSourceMap;

    @PostConstruct
    public void doInit() {
        System.out.println(1);
        typeDataSourceMap = new HashMap(){{
            put(SearchTypeEnum.POST.getValue(), postDataSource);
            put(SearchTypeEnum.PICTURE.getValue(), pictureDataSource);
            put(SearchTypeEnum.USER.getValue(), userDataSource);
        }};
    }


    public DataSource getDataSourceByType(String type){
        if(typeDataSourceMap == null){
            return null;
        }
        return typeDataSourceMap.get(type);
    }

}
