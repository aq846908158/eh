package cn.lcvc.POJO;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Producttype {
    private Integer id;
    private String productTypeName;
    private Integer productTypeRank;
    private String productTypeCode;
    private String superType;

    @Id
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "productTypeName")
    public String getProductTypeName() {
        return productTypeName;
    }

    public void setProductTypeName(String productTypeName) {
        this.productTypeName = productTypeName;
    }

    @Basic
    @Column(name = "productTypeRank")
    public Integer getProductTypeRank() {
        return productTypeRank;
    }

    public void setProductTypeRank(Integer productTypeRank) {
        this.productTypeRank = productTypeRank;
    }

    @Basic
    @Column(name = "productTypeCode")
    public String getProductTypeCode() {
        return productTypeCode;
    }

    public void setProductTypeCode(String productTypeCode) {
        this.productTypeCode = productTypeCode;
    }

    @Basic
    @Column(name = "superType")
    public String getSuperType() {
        return superType;
    }

    public void setSuperType(String superType) {
        this.superType = superType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Producttype that = (Producttype) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (productTypeName != null ? !productTypeName.equals(that.productTypeName) : that.productTypeName != null)
            return false;
        if (productTypeRank != null ? !productTypeRank.equals(that.productTypeRank) : that.productTypeRank != null)
            return false;
        if (productTypeCode != null ? !productTypeCode.equals(that.productTypeCode) : that.productTypeCode != null)
            return false;
        if (superType != null ? !superType.equals(that.superType) : that.superType != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (productTypeName != null ? productTypeName.hashCode() : 0);
        result = 31 * result + (productTypeRank != null ? productTypeRank.hashCode() : 0);
        result = 31 * result + (productTypeCode != null ? productTypeCode.hashCode() : 0);
        result = 31 * result + (superType != null ? superType.hashCode() : 0);
        return result;
    }
}
