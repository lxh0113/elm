package com.elm.dao.responseData;

import com.elm.domain.Store;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RecommendData {

    private String title;

    private String subTitle;

    private List<Store> list;

}
