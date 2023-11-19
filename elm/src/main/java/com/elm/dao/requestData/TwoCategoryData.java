package com.elm.dao.requestData;

import com.elm.domain.Category;

import java.util.ArrayList;
import java.util.Calendar;

public class TwoCategoryData {
    private ArrayList<Category> list;

    public TwoCategoryData() {
    }

    public TwoCategoryData(ArrayList<Category> list) {
        this.list = list;
    }

    public ArrayList<Category> getList() {
        return list;
    }

    public void setList(ArrayList<Category> list) {
        this.list = list;
    }
}
