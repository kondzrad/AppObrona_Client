package pl.kawka.appobrona.model;

public class EmployeeLogin {  //odwzorowanie modelu z Serwera

    Integer id;
    String first_name, last_name, status, login, password;

    public EmployeeLogin(Integer id, String first_name, String last_name, String status, String login, String password) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.status = status;
        this.login = login;
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
