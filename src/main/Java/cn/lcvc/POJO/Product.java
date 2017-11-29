package cn.lcvc.POJO;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
public class Product {
    private Integer id;
    private String productName;
    private Integer productNumber;
    private Integer productType;
    private Double productPrice;
    private Integer school;
    private String productIntroduce;
    private Integer degree;
    private Boolean grounding;
    private Timestamp buyTime;
    private Timestamp expire;
    private Integer user;
    private Integer seeNumber;

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
    @Column(name = "productType")
    public Integer getProductType() {
        return productType;
    }

    public void setProductType(Integer productType) {
        this.productType = productType;
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
    @Column(name = "school")
    public Integer getSchool() {
        return school;
    }

    public void setSchool(Integer school) {
        this.school = school;
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
    @Column(name = "user")
    public Integer getUser() {
        return user;
    }

    public void setUser(Integer user) {
        this.user = user;
    }

    @Basic
    @Column(name = "seeNumber")
    public Integer getSeeNumber() {
        return seeNumber;
    }

    public void setSeeNumber(Integer seeNumber) {
        this.seeNumber = seeNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        if (id != null ? !id.equals(product.id) : product.id != null) return false;
        if (productName != null ? !productName.equals(product.productName) : product.productName != null) return false;
        if (productNumber != null ? !productNumber.equals(product.productNumber) : product.productNumber != null)
            return false;
        if (productType != null ? !productType.equals(product.productType) : product.productType != null) return false;
        if (productPrice != null ? !productPrice.equals(product.productPrice) : product.productPrice != null)
            return false;
        if (school != null ? !school.equals(product.school) : product.school != null) return false;
        if (productIntroduce != null ? !productIntroduce.equals(product.productIntroduce) : product.productIntroduce != null)
            return false;
        if (degree != null ? !degree.equals(product.degree) : product.degree != null) return false;
        if (grounding != null ? !grounding.equals(product.grounding) : product.grounding != null) return false;
        if (buyTime != null ? !buyTime.equals(product.buyTime) : product.buyTime != null) return false;
        if (expire != null ? !expire.equals(product.expire) : product.expire != null) return false;
        if (user != null ? !user.equals(product.user) : product.user != null) return false;
        if (seeNumber != null ? !seeNumber.equals(product.seeNumber) : product.seeNumber != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (productName != null ? productName.hashCode() : 0);
        result = 31 * result + (productNumber != null ? productNumber.hashCode() : 0);
        result = 31 * result + (productType != null ? productType.hashCode() : 0);
        result = 31 * result + (productPrice != null ? productPrice.hashCode() : 0);
        result = 31 * result + (school != null ? school.hashCode() : 0);
        result = 31 * result + (productIntroduce != null ? productIntroduce.hashCode() : 0);
        result = 31 * result + (degree != null ? degree.hashCode() : 0);
        result = 31 * result + (grounding != null ? grounding.hashCode() : 0);
        result = 31 * result + (buyTime != null ? buyTime.hashCode() : 0);
        result = 31 * result + (expire != null ? expire.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (seeNumber != null ? seeNumber.hashCode() : 0);
        return result;
    }
}
