package ua.kiev.prog.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="Task_List")
public class Task {
    @Id
    @GeneratedValue
    private long id;
    
    @ManyToOne
    @JoinColumn(name="group_id")
    private Group group;

    @Column(name = "task")
    private String taskName;

    @Column(name = "date")
    private Date taskDate;

    private String phone;
    private String email;

    public Task() {}

    public Task(Group group, String taskName, Date taskDate, String phone, String email) {
        this.group = group;
        this.taskName = taskName;
        this.taskDate = taskDate;
        this.phone = phone;
        this.email = email;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getTaskDate() {
        return taskDate;
    }

    public void setTaskDate(Date surname) {
        this.taskDate = surname;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String name) {
        this.taskName = name;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }
}
