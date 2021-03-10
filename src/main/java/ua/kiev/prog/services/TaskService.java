package ua.kiev.prog.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.kiev.prog.repositories.GroupRepository;
import ua.kiev.prog.repositories.TaskRepository;
import ua.kiev.prog.model.Group;
import ua.kiev.prog.model.Task;

import java.util.Date;
import java.util.List;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private GroupRepository groupRepository;

    @Transactional
    public void addTask(Task taskName) {
        taskRepository.save(taskName);
    }

    @Transactional
    public void addGroup(Group group) {
        groupRepository.save(group);
    }

    @Transactional
    public void deleteTasks(long[] idList) {
        for (long id : idList)
            taskRepository.deleteById(id);
    }

    @Transactional(readOnly=true)
    public List<Group> findGroups() {
        return groupRepository.findAll();
    }

    @Transactional(readOnly=true)
    public List<Task> findAll(Pageable pageable) {
        return taskRepository.findAll(pageable).getContent();
    }

    @Transactional(readOnly=true)
    public List<Task> findByGroup(Group group, Pageable pageable) {
        return taskRepository.findByGroup(group, pageable);
    }

    @Transactional(readOnly = true)
    public long countByGroup(Group group) {
        return taskRepository.countByGroup(group);
    }

    @Transactional(readOnly=true)
    public List<Task> findByPattern(String pattern, Pageable pageable) {
        return taskRepository.findByPattern(pattern, pageable);
    }

    @Transactional(readOnly = true)
    public long count() {
        return taskRepository.count();
    }

    @Transactional(readOnly=true)
    public Group findGroup(long id) {
        return groupRepository.findById(id).get();
    }

    @Transactional
    public void reset() {
        groupRepository.deleteAll();
        taskRepository.deleteAll();

        Group group = new Group("Test");
        Date curDate = new Date();
        Task taskName;

        addGroup(group);

        for (int i = 0; i < 13; i++) {
            taskName = new Task(null, "Task" + i, new Date(curDate.getTime() + i * 60000 + 600000),
                    "1234567" + i, "user" + i + "@test.com");
            addTask(taskName);
        }
        for (int i = 0; i < 10; i++) {
            taskName = new Task(group, "Other" + i, new Date(curDate.getTime() + i * 60000),
                    "7654321" + i, "user" + i + "@other.com");
            addTask(taskName);
        }
    }
}
