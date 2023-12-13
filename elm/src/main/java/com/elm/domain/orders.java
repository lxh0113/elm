package com.elm.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.elm.dao.requestData.OrderItemsData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

@NoArgsConstructor
@AllArgsConstructor
@Data
@TableName("orders")
public class Orders implements Delayed{

    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField("buyer_id")
    private String buyerId;

    @TableField("store_id")
    private String storeId;

    private Double price;

    @TableField("address_text")
    private String addressText;

    @TableField("package_fee")
    private Double packageFee;

    private Integer status;

    private Date time;

    private String remark;

    @TableField("is_cancel")
    private Integer isCancel;

    @TableField("cancel_reason")
    private String cancelReason;

    //订单细节
    @TableField("orderInfo")
    private String orderInfo;

    @TableField("comment_id")
    private Integer commentId;

    @TableField(exist = false)
    private List<OrderItemsData> itemsLists;

    @TableField(exist = false)
    private Integer number;

    @TableField("buyer_name")
    private String buyerName;

    @TableField("buyer_tel")
    private String buyerTel;

    private String pcd;

    @TableField(exist = false)
    private Integer current;

    @TableField(exist = false)
    private String storeUrl;

    @TableField(exist = false)
    private String storeName;

    public Orders(Integer id, String buyerId, String storeId, Double price, String addressText, Double packageFee, Integer status, Date time, String remark, Integer isCancel, String cancelReason, String orderInfo, Integer commentId, List<OrderItemsData> itemsLists, Integer number, String buyerName, String buyerTel, String pcd) {
        this.id = id;
        this.buyerId = buyerId;
        this.storeId = storeId;
        this.price = price;
        this.addressText = addressText;
        this.packageFee = packageFee;
        this.status = status;
        this.time = time;
        this.remark = remark;
        this.isCancel = isCancel;
        this.cancelReason = cancelReason;
        this.orderInfo = orderInfo;
        this.commentId = commentId;
        this.itemsLists = itemsLists;
        this.number = number;
        this.buyerName = buyerName;
        this.buyerTel = buyerTel;
        this.pcd = pcd;
    }

    @Override
    public String toString() {
        return "Orders{" +
                "id=" + id +
                ", buyerId='" + buyerId + '\'' +
                ", storeId='" + storeId + '\'' +
                ", price=" + price +
                ", addressText='" + addressText + '\'' +
                ", packageFee=" + packageFee +
                ", status=" + status +
                ", time=" + time +
                ", remark='" + remark + '\'' +
                ", isCancel=" + isCancel +
                ", cancelReason='" + cancelReason + '\'' +
                ", orderInfo='" + orderInfo + '\'' +
                ", commentId='" + commentId + '\'' +
                ", itemsLists=" + itemsLists +
                ", number=" + number +
                ", buyerName='" + buyerName + '\'' +
                ", buyerTel='" + buyerTel + '\'' +
                ", pcd='" + pcd + '\'' +
                '}';
    }

    @Override
    public long getDelay(TimeUnit unit) {
        //上面用到unit.convert()办法，其实在这个小场景不须要用到，只是学习一下如何应用罢了
        return unit.convert(time.getTime() - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
    }


    @Override
    public int compareTo(Delayed o) {
        //这里依据勾销工夫来比拟，如果勾销所剩时间小的，就会优先被队列提取进去
        //注意延迟时间 的绑定就是这绑定的属性
        return this.getTime().compareTo(((Orders) o).getTime());
    }

}
