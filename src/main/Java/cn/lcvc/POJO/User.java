package cn.lcvc.POJO;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
public class User {
    private Integer id;
    private String userName;
    private String userPassword;
    private String salt;
    private String trueName;
    private Integer school;
    private Integer sellNumber;
    private Integer forSaleNumber;
    private String phone;
    private String email;
    private Timestamp createTime;
    private Timestamp lastTime;

    @Id
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "userName")
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Basic
    @Column(name = "userPassword")
    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    @Basic
    @Column(name = "salt")
    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    @Basic
    @Column(name = "trueName")
    public String getTrueName() {
        return trueName;
    }

    public void setTrueName(String trueName) {
        this.trueName = trueName;
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
    @Column(name = "sellNumber")
    public Integer getSellNumber() {
        return sellNumber;
    }

    public void setSellNumber(Integer sellNumber) {
        this.sellNumber = sellNumber;
    }

    @Basic
    @Column(name = "forSaleNumber")
    public Integer getForSaleNumber() {
        return forSaleNumber;
    }

    public void setForSaleNumber(Integer forSaleNumber) {
        this.forSaleNumber = forSaleNumber;
    }

    @Basic
    @Column(name = "phone")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Basic
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
    @Column(name = "lastTime")
    public Timestamp getLastTime() {
        return lastTime;
    }

    public void setLastTime(Timestamp lastTime) {
        this.lastTime = lastTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != null ? !id.equals(user.id) : user.id != null) return false;
        if (userName != null ? !userName.equals(user.userName) : user.userName != null) return false;
        if (userPassword != null ? !userPassword.equals(user.userPassword) : user.userPassword != null) return false;
        if (salt != null ? !salt.equals(user.salt) : user.salt != null) return false;
        if (trueName != null ? !trueName.equals(user.trueName) : user.trueName != null) return false;
        if (school != null ? !school.equals(user.school) : user.school != null) return false;
        if (sellNumber != null ? !sellNumber.equals(user.sellNumber) : user.sellNumber != null) return false;
        if (forSaleNumber != null ? !forSaleNumber.equals(user.forSaleNumber) : user.forSaleNumber != null)
            return false;
        if (phone != null ? !phone.equals(user.phone) : user.phone != null) return false;
        if (email != null ? !email.equals(user.email) : user.email != null) return false;
        if (createTime != null ? !createTime.equals(user.createTime) : user.createTime != null) return false;
        if (lastTime != null ? !lastTime.equals(user.lastTime) : user.lastTime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        result = 31 * result + (userPassword != null ? userPassword.hashCode() : 0);
        result = 31 * result + (salt != null ? salt.hashCode() : 0);
        result = 31 * result + (trueName != null ? trueName.hashCode() : 0);
        result = 31 * result + (school != null ? school.hashCode() : 0);
        result = 31 * result + (sellNumber != null ? sellNumber.hashCode() : 0);
        result = 31 * result + (forSaleNumber != null ? forSaleNumber.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (lastTime != null ? lastTime.hashCode() : 0);
        return result;
    }
}
