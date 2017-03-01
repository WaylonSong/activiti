package activiti.controller;

import activiti.service.ActivityService;
import org.activiti.engine.FormService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.form.FormData;
import org.activiti.engine.form.FormProperty;
import org.activiti.engine.form.StartFormData;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
/**
 * Created by song on 2017/1/7.
 */
@Controller
@RequestMapping(value = "/activity")
public class ActivitiController {

    @Resource
    private ActivityService activityService;
    @Resource
    private FormService formService;
    @Resource
    private RepositoryService repositoryService;

    @RequestMapping(value="/process/deploy/{fileName}", method= RequestMethod.GET)
    @ResponseBody
    public String deploy(@PathVariable String fileName) {
        activityService.deploy(fileName);
        return "deploy done";
    }

    @RequestMapping(value="/process/start/{key}", method= RequestMethod.GET)
    public String start(@PathVariable String key) {
        return "activiti/start";
    }

    @RequestMapping(value="/process/startForm/{processKey}", method= RequestMethod.POST)
    @ResponseBody
        public String startForm(@PathVariable String processKey, HttpServletRequest request) {
        String userId = "Huang";//        request.getSession().getAttribute("userId");
        String processInstanceId = activityService.startForm(userId, processKey, request.getParameterMap());
        return processInstanceId;
    }

    @RequestMapping(value="/process/startForm/{processKey}", method= RequestMethod.GET)
    @ResponseBody
    public List<FormProperty> getStartFormProperties(@PathVariable String processKey) {
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionKey(processKey).latestVersion().singleResult();
        //获取开始表单
        StartFormData startFormData = formService.getStartFormData(processDefinition.getId());
//        Object formString = formService.getRenderedStartForm(processDefinition.getId());
        if(startFormData != null){
            return startFormData.getFormProperties();
        }
        return null;
    }


    @RequestMapping(value="/tasks", method= RequestMethod.GET, produces= MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<TaskRepresentation> getTasks(@RequestParam String assignee) {
        List<Task> tasks = activityService.getTasks(assignee);
        List<TaskRepresentation> dtos = new ArrayList<TaskRepresentation>();
        for (Task task : tasks) {
            dtos.add(new TaskRepresentation(task.getId(), task.getName()));
        }
        return dtos;
    }

    @RequestMapping(value="/task/{id}", method= RequestMethod.GET)
    @ResponseBody
    public String taskNextStep(@PathVariable String id) {
        activityService.viewTask(id);
        return null;
    }

    @RequestMapping(value="/task/{id}", method= RequestMethod.POST)
    @ResponseBody
    public String taskComplete(@PathVariable String id, HttpServletRequest request) {
        activityService.complete(id, request.getParameterMap());
        return null;
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
