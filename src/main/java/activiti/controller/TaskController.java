package activiti.controller;

import activiti.service.ActivityService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by song on 2017/3/27.
 */
@Controller
@RequestMapping(value = "/activity/task")
public class TaskController {

    @Resource
    private ActivityService activityService;

    @RequestMapping(value="/list", method= RequestMethod.GET)
    public String toList() {
        return "activity/task/list";
    }

    @RequestMapping(value="/history", method= RequestMethod.GET)
    public String history() {
        return "activity/task/history";
    }

    @RequestMapping(value="/execute/{taskId}", method= RequestMethod.GET)
    public String toExecute() {
        return "activity/task/execute";
    }


    @RequestMapping(value="/{id}", method= RequestMethod.GET)
    public Map taskNextStep(@PathVariable String id) {
        return activityService.viewTask(id);
    }

    @RequestMapping(value="/{id}", method= RequestMethod.POST)
    public String taskComplete(@PathVariable String id, HttpServletRequest request) {
        activityService.complete(id, request.getParameterMap());
        return "activity/task/list";
    }

    static class TaskRepresentation {

        private String id;
        private String name;

        public TaskRepresentation(String id, String name) {
            this.id = id;
            this.name = name;
        }
        public String getId() {
            return id;
        }
        public void setId(String id) {
            this.id = id;
        }
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }

    }
}
