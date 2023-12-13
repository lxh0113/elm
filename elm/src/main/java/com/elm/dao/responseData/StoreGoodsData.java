package com.elm.dao.responseData;

import com.elm.domain.Goods;
import com.elm.domain.Store;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Negative;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoreGoodsData {
    private Store store;
    private List<Goods> list;
}
