package com.yupi.springbootinit.model.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @Date: 2023/11/14 18:45
 * @Author: Lowell
 * @Description:
 **/
@Data
public class Picture implements Serializable {
    private String title;
    private String url;

    private static final long serialVersionUID = 1L;

}
