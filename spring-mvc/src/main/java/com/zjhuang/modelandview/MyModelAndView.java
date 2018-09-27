package com.zjhuang.modelandview;

import java.util.HashMap;
import java.util.Map;

/**
 * 数据和视图对象
 *
 * @author zjhuang
 * @create 2018/9/26 18:58
 **/
public class MyModelAndView {
    /**
     * 视图名称/路径
     */
    private String viewName;
    /**
     * 数据集合
     */
    private Map<String, Object> models = new HashMap<String, Object>();

    public MyModelAndView(String viewName) {
        this.viewName = viewName;
    }

    public String getViewName() {
        return viewName;
    }

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }

    public Map<String, Object> getModels() {
        return models;
    }

    public void setModels(Map<String, Object> models) {
        this.models = models;
    }

    @Override
    public String toString() {
        return "MyModelAndView{" +
                "viewName='" + viewName + '\'' +
                ", models=" + models +
                '}';
    }
}
