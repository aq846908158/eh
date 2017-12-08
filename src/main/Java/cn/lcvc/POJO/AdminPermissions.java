package cn.lcvc.POJO;

import javax.persistence.*;

@Entity
public class AdminPermissions {
    private Integer id;
    private Boolean low;
    private Boolean middle;
    private Boolean height;
    private Admin admin;

    @Id
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
    @Column(name = "middle")
    public Boolean getMiddle() {
        return middle;
    }

    public void setMiddle(Boolean middle) {
        this.middle=middle;
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

        AdminPermissions that = (AdminPermissions) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (low != null ? !low.equals(that.low) : that.low != null) return false;
        if (middle != null ? !middle.equals(that.middle) : that.middle != null) return false;
        if (height != null ? !height.equals(that.height) : that.height != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (low != null ? low.hashCode() : 0);
        result = 31 * result + (middle != null ? middle.hashCode() : 0);
        result = 31 * result + (height != null ? height.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "admin", referencedColumnName = "id", nullable = false)
    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }
}
