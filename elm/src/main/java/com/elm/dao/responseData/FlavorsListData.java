package com.elm.dao.responseData;

import com.elm.domain.Flavors;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class FlavorsListData {

    private String name;

    private ArrayList<Flavors> flavors;

    @Override
    public String toString() {
        return "FlavorsListData{" +
                "name='" + name + '\'' +
                ", flavors=" + flavors +
                '}';
    }
}
