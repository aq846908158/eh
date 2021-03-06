package cn.lcvc.POJO;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class Product {
    private Integer id;
    private String productName;
    private Integer productNumber;
    private Double productPrice;
    private String productIntroduce;
    private Integer degree;
    private Boolean grounding;
    private Timestamp buyTime;
    private Timestamp expire;
    private Integer seeNumber;
    private ProductType productType;
    private School school;
    private User user;
    private Timestamp criateTime;
    private Boolean state;
    private  String imgUrl; //图片路径



    @Id
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "productName")
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    @Basic
    @Column(name = "productNumber")
    public Integer getProductNumber() {
        return productNumber;
    }

    public void setProductNumber(Integer productNumber) {
        this.productNumber = productNumber;
    }

    @Basic
    @Column(name = "productPrice")
    public Double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }

    @Basic
    @Column(name = "productIntroduce")
    public String getProductIntroduce() {
        return productIntroduce;
    }

    public void setProductIntroduce(String productIntroduce) {
        this.productIntroduce = productIntroduce;
    }

    @Basic
    @Column(name = "degree")
    public Integer getDegree() {
        return degree;
    }

    public void setDegree(Integer degree) {
        this.degree = degree;
    }

    @Basic
    @Column(name = "grounding")
    public Boolean getGrounding() {
        return grounding;
    }

    public void setGrounding(Boolean grounding) {
        this.grounding = grounding;
    }

    @Basic
    @Column(name = "buyTime")
    public Timestamp getBuyTime() {
        return buyTime;
    }

    public void setBuyTime(Timestamp buyTime) {
        this.buyTime = buyTime;
    }

    @Basic
    @Column(name = "expire")
    public Timestamp getExpire() {
        return expire;
    }

    public void setExpire(Timestamp expire) {
        this.expire = expire;
    }

    @Basic
    @Column(name = "seeNumber")
    public Integer getSeeNumber() {
        return seeNumber;
    }

    public void setSeeNumber(Integer seeNumber) {
        this.seeNumber = seeNumber;
    }

    @Basic
    @Column(name = "criateTime")
    public Timestamp getCriateTime() {
        return criateTime;
    }

    public void setCriateTime(Timestamp criateTime) {
        this.criateTime = criateTime;
    }

    @Basic
    @Column(name = "state")
    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    @Basic
    @Column(name = "imgUrl")
    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    @Override
    public boolean equals(Object o) {
        Product product = (Product) o;
        if (this.getId().equals(product.getId())) return true;
        return false;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (productName != null ? productName.hashCode() : 0);
        result = 31 * result + (productNumber != null ? productNumber.hashCode() : 0);
        result = 31 * result + (productPrice != null ? productPrice.hashCode() : 0);
        result = 31 * result + (productIntroduce != null ? productIntroduce.hashCode() : 0);
        result = 31 * result + (degree != null ? degree.hashCode() : 0);
        result = 31 * result + (grounding != null ? grounding.hashCode() : 0);
        result = 31 * result + (buyTime != null ? buyTime.hashCode() : 0);
        result = 31 * result + (expire != null ? expire.hashCode() : 0);
        result = 31 * result + (seeNumber != null ? seeNumber.hashCode() : 0);
        result = 31 * result + (productType != null ? productType.hashCode() : 0);
        result = 31 * result + (school != null ? school.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (criateTime != null ? criateTime.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        result = 31 * result + (imgUrl != null ? imgUrl.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "productType", referencedColumnName = "id", nullable = false)
    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    @ManyToOne
    @JoinColumn(name = "school", referencedColumnName = "id")
    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    @ManyToOne
    @JoinColumn(name = "user", referencedColumnName = "id", nullable = false)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
