package ua.kiev.prog.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ua.kiev.prog.model.Group;
import ua.kiev.prog.model.Task;
import ua.kiev.prog.services.TaskService;

import java.text.SimpleDateFormat;
import java.util.Date;

@Configuration
public class AppConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/static/**")
                .addResourceLocations("/WEB-INF/static/");
    }

    @Bean
    public CommandLineRunner demo(final TaskService taskService) {
        return new CommandLineRunner() {
            @Override
            public void run(String... strings) throws Exception {
                Group group = new Group("Test");
                Date curDate = new Date();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
                Task taskName;

                taskService.addGroup(group);

                for (int i = 0; i < 13; i++) {
                    taskName = new Task(null, "Task" + i, new Date(curDate.getTime() + i * 60000 + 600000),
                            "1234567" + i, "user" + i + "@test.com");
                    taskService.addTask(taskName);
                }
                for (int i = 0; i < 10; i++) {
                    taskName = new Task(group, "Other" + i, new Date(curDate.getTime() + i * 60000),
                            "7654321" + i, "user" + i + "@other.com");
                    taskService.addTask(taskName);
                }
            }
        };
    }
}
