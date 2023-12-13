package com.elm.dao.requestData;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentsSearchData {
    private String userId;
    private Integer current;
}
