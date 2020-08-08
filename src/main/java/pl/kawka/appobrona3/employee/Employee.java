package pl.kawka.appobrona3.employee;

public class Employee {


    public Employee(String number, String title, String tags) {
        this.number = number;
        this.title = title;
        this.tags = tags;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    String number, title, tags;




}
