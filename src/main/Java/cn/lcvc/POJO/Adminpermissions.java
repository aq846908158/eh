package cn.lcvc.POJO;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Adminpermissions {
    private Integer id;
    private Integer admin;
    private Boolean low;
    private Boolean in;
    private Boolean height;

    @Id
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "admin")
    public Integer getAdmin() {
        return admin;
    }

    public void setAdmin(Integer admin) {
        this.admin = admin;
    }

    @Basic
    @Column(name = "low")
    public Boolean getLow() {
        return low;
    }

    public void setLow(Boolean low) {
        this.low = low;
    }

    @Basic
    @Column(name = "in")
    public Boolean getIn() {
        return in;
    }

    public void setIn(Boolean in) {
        this.in = in;
    }

    @Basic
    @Column(name = "height")
    public Boolean getHeight() {
        return height;
    }

    public void setHeight(Boolean height) {
        this.height = height;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Adminpermissions that = (Adminpermissions) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (admin != null ? !admin.equals(that.admin) : that.admin != null) return false;
        if (low != null ? !low.equals(that.low) : that.low != null) return false;
        if (in != null ? !in.equals(that.in) : that.in != null) return false;
        if (height != null ? !height.equals(that.height) : that.height != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (admin != null ? admin.hashCode() : 0);
        result = 31 * result + (low != null ? low.hashCode() : 0);
        result = 31 * result + (in != null ? in.hashCode() : 0);
        result = 31 * result + (height != null ? height.hashCode() : 0);
        return result;
    }
}
