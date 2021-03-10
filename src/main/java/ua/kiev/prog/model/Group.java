package ua.kiev.prog.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="Groups1")
public class Group {
    @Id
    @GeneratedValue
    private long id;
    private String name;

    @OneToMany(mappedBy="group", cascade=CascadeType.ALL)
    private List<Task> tasks = new ArrayList<Task>();

    public Group() {}

    public Group(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}
