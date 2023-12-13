package com.elm.dao.requestData;

import com.elm.dao.responseData.FlavorsListData;
import com.elm.domain.Goods;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DishData {

    private Goods goods;

    private ArrayList<FlavorsListData> flavorsListData;
}
