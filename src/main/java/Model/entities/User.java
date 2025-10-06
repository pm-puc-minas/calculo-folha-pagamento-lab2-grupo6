package Model.entities;

public class User {
    private String userLogin;
    private String password;

    public User(String userLogin,String password)
    {
        this.userLogin = userLogin;
        this.password = password;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
