package top.zifanxiansheng.middleware.db.router.model;

/**
 * @Author 梓樊先生
 * @Date 2021/12/28 17:51
 **/
public class DBProperties {

    private String url;
    private String userName;
    private String password;


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
