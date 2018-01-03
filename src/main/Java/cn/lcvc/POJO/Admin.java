package cn.lcvc.POJO;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
public class Admin {
    private Integer id;
    private String userName;
    private String userPassword;
    private String salt;
    private String trueName;
    private String phone;
    private String email;
    private Timestamp createTime;
    private Timestamp lastTime;//最后登录时间
    private Timestamp loginLastTime;//上一次登录时间
    private Integer loginState; //登录状态 0表示允许登录 1表示禁止登录
    private Integer loginNum; //登录次数
    private String title;


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

    @Basic
    @Column(name = "loginLastTime")
    public Timestamp getLoginLastTime() {
        return loginLastTime;
    }

    public void setLoginLastTime(Timestamp loginLastTime) {
        this.loginLastTime = loginLastTime;
    }

    @Basic
    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "loginState")
    public Integer getLoginState() {
        return loginState;
    }

    public void setLoginState(Integer loginState) {
        this.loginState = loginState;
    }

    @Basic
    @Column(name = "loginNum")
    public Integer getLoginNum() {
        return loginNum;
    }

    public void setLoginNum(Integer loginNum) {
        this.loginNum = loginNum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Admin admin = (Admin) o;

        if (id != null ? !id.equals(admin.id) : admin.id != null) return false;
        if (userName != null ? !userName.equals(admin.userName) : admin.userName != null) return false;
        if (userPassword != null ? !userPassword.equals(admin.userPassword) : admin.userPassword != null) return false;
        if (salt != null ? !salt.equals(admin.salt) : admin.salt != null) return false;
        if (trueName != null ? !trueName.equals(admin.trueName) : admin.trueName != null) return false;
        if (phone != null ? !phone.equals(admin.phone) : admin.phone != null) return false;
        if (email != null ? !email.equals(admin.email) : admin.email != null) return false;
        if (createTime != null ? !createTime.equals(admin.createTime) : admin.createTime != null) return false;
        if (lastTime != null ? !lastTime.equals(admin.lastTime) : admin.lastTime != null) return false;
        if (loginLastTime != null ? !loginLastTime.equals(admin.loginLastTime) : admin.loginLastTime != null)
            return false;
        if (loginState != null ? !loginState.equals(admin.loginState) : admin.loginState != null) return false;
        if (loginNum != null ? !loginNum.equals(admin.loginNum) : admin.loginNum != null) return false;
        return title != null ? title.equals(admin.title) : admin.title == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        result = 31 * result + (userPassword != null ? userPassword.hashCode() : 0);
        result = 31 * result + (salt != null ? salt.hashCode() : 0);
        result = 31 * result + (trueName != null ? trueName.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (lastTime != null ? lastTime.hashCode() : 0);
        result = 31 * result + (loginLastTime != null ? loginLastTime.hashCode() : 0);
        result = 31 * result + (loginState != null ? loginState.hashCode() : 0);
        result = 31 * result + (loginNum != null ? loginNum.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        return result;
    }
}
