package cn.lcvc.POJO;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class Order {
    private Integer id;
    private String orderCode;
    private Integer number;
    private Double orderPrice;
    private Boolean chalkUp;
    private Timestamp createTime;
    private String message;
    private User buyUser;
    private User sellUser;

    @Id
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "orderCode")
    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    @Basic
    @Column(name = "number")
    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    @Basic
    @Column(name = "orderPrice")
    public Double getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(Double orderPrice) {
        this.orderPrice = orderPrice;
    }

    @Basic
    @Column(name = "chalkUp")
    public Boolean getChalkUp() {
        return chalkUp;
    }

    public void setChalkUp(Boolean chalkUp) {
        this.chalkUp = chalkUp;
    }

    @Basic
    @Column(name = "createTime")
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Basic
    @Column(name = "message")
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        if (id != null ? !id.equals(order.id) : order.id != null) return false;
        if (orderCode != null ? !orderCode.equals(order.orderCode) : order.orderCode != null) return false;
        if (number != null ? !number.equals(order.number) : order.number != null) return false;
        if (orderPrice != null ? !orderPrice.equals(order.orderPrice) : order.orderPrice != null) return false;
        if (chalkUp != null ? !chalkUp.equals(order.chalkUp) : order.chalkUp != null) return false;
        if (createTime != null ? !createTime.equals(order.createTime) : order.createTime != null) return false;
        if (message != null ? !message.equals(order.message) : order.message != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (orderCode != null ? orderCode.hashCode() : 0);
        result = 31 * result + (number != null ? number.hashCode() : 0);
        result = 31 * result + (orderPrice != null ? orderPrice.hashCode() : 0);
        result = 31 * result + (chalkUp != null ? chalkUp.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (message != null ? message.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "sellUser", referencedColumnName = "id", nullable = false)
    public User getBuyUser() {
        return buyUser;
    }

    public void setBuyUser(User buyUser) {
        this.buyUser = buyUser;
    }

    @ManyToOne
    @JoinColumn(name = "buyUser", referencedColumnName = "id", nullable = false)
    public User getSellUser() {
        return sellUser;
    }

    public void setSellUser(User sellUser) {
        this.sellUser = sellUser;
    }
}
