package com.elm.dao.responseData;

import com.elm.domain.Goods;
import com.elm.domain.ShopCart;
import com.elm.domain.Store;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShopCartResData {
    private Store store;

    private List<ShopCart> shopCarts;
}
