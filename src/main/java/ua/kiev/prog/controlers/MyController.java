package ua.kiev.prog.controlers;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.kiev.prog.model.Group;
import ua.kiev.prog.model.Task;
import ua.kiev.prog.services.ScheduledTask;
import ua.kiev.prog.services.TaskService;

import java.util.Date;
import java.util.List;
import java.util.Timer;

@Controller
public class MyController {
    static final int DEFAULT_GROUP_ID = -1;
    static final int ITEMS_PER_PAGE = 6;

    //@Autowired
    private final TaskService taskService;

    public MyController(TaskService taskService) {
        this.taskService = taskService;
    }

    @RequestMapping("/")
    public String index(Model model,
                        @RequestParam(required = false,
                                defaultValue = "0") Integer page) {
        if (page < 0) page = 0;

        List<Task> tasks = taskService
                .findAll(PageRequest.of(page,
                        ITEMS_PER_PAGE,
                        Sort.Direction.DESC, "id"));

        model.addAttribute("groups", taskService.findGroups());
        model.addAttribute("tasks", tasks);
        model.addAttribute("allPages", getPageCount());

        return "index";
    }

    @RequestMapping("/reset")
    public String reset() {
        taskService.reset();
        return "redirect:/";
    }

    @RequestMapping("/task_add_page")
    public String taskAddPage(Model model) {
        model.addAttribute("groups", taskService.findGroups());
        return "task_add_page";
    }

    @GetMapping("/group_add_page")
    public String groupAddPage() {
        return "group_add_page";
    }

    @GetMapping("/group/{id}")
    public String listGroup(
            @PathVariable(value = "id") long groupId,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            Model model)
    {
        Group group = (groupId != DEFAULT_GROUP_ID) ? taskService.findGroup(groupId) : null;
        if (page < 0) page = 0;

        List<Task> tasks = taskService
                .findByGroup(group, PageRequest.of(page, ITEMS_PER_PAGE, Sort.Direction.DESC, "id"));

        model.addAttribute("groups", taskService.findGroups());
        model.addAttribute("tasks", tasks);
        model.addAttribute("byGroupPages", getPageCount(group));
        model.addAttribute("groupId", groupId);

        return "index";
    }

    @PostMapping(value = "/search")
    public String search(@RequestParam String pattern, Model model) {
        model.addAttribute("groups", taskService.findGroups());
        model.addAttribute("tasks", taskService.findByPattern(pattern, null));

        return "index";
    }

    @RequestMapping(value = "/task/delete", method = RequestMethod.POST)
    public ResponseEntity<Void> delete(@RequestParam(value = "ids[]", required = false)
                                                   long[] ids) {
        if (ids != null && ids.length > 0)
            taskService.deleteTasks(ids);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value="/task/add")
    public String taskAdd(@RequestParam(value = "group") long groupId,
                          @RequestParam String taskName,
                          @RequestParam("taskDate") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") Date taskDate,
                          @RequestParam String phone,
                          @RequestParam String email)
    {
        Group group = (groupId != DEFAULT_GROUP_ID) ? taskService.findGroup(groupId) : null;

        Task task = new Task(group, taskName, taskDate, phone, email);
        taskService.addTask(task);
        Timer timer = new Timer();
        timer.schedule(new ScheduledTask(taskName, phone), taskDate);

        return "redirect:/";
    }

    @RequestMapping(value="/group/add", method = RequestMethod.POST)
    public String groupAdd(@RequestParam String name) {
        taskService.addGroup(new Group(name));
        return "redirect:/";
    }

    private long getPageCount() {
        long totalCount = taskService.count();
        return (totalCount / ITEMS_PER_PAGE) + ((totalCount % ITEMS_PER_PAGE > 0) ? 1 : 0);
    }

    private long getPageCount(Group group) {
        long totalCount = taskService.countByGroup(group);
        return (totalCount / ITEMS_PER_PAGE) + ((totalCount % ITEMS_PER_PAGE > 0) ? 1 : 0);
    }
}
