package com.elm.dao.requestData;

import com.elm.domain.Address;
import com.elm.domain.Store;
import com.elm.domain.Store_env;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ChangeStoreData {

    private Store store;

    private Address address;

    private List<Store_env> storeEnv;

    @Override
    public String toString() {
        return "ChangeStoreData{" +
                "store=" + store +
                ", address=" + address +
                ", storeEnv=" + storeEnv +
                '}';
    }
}
