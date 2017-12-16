package cn.lcvc.POJO;

public class TokenMessage {
    private String sub;//主题
    private String iss;//发行者
    private String exp;//过期时间
    private String userId;//userId
    private String iat ;//发行时间
    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public String getIss() {
        return iss;
    }

    public void setIss(String iss) {
        this.iss = iss;
    }

    public String getExp() {
        return exp;
    }

    public void setExp(String exp) {
        this.exp = exp;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserid(String userId) {
        this.userId = userId;
    }

    public String getIat() {
        return iat;
    }

    public void setIat(String iat) {
        this.iat = iat;
    }
}
