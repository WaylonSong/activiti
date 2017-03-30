package activiti.controller;

import activiti.service.ActivityService;
import org.activiti.engine.FormService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.form.StartFormData;
import org.activiti.engine.repository.ProcessDefinition;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import util.Result;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by song on 2017/1/7.
 */
@Controller
@RequestMapping(value = "/activity/process")
public class ProcessController {

    @Resource
    private ActivityService activityService;
    @Resource
    private FormService formService;
    @Resource
    private RepositoryService repositoryService;
    @Resource
    private ResultWrapper resultWrapper;

    @RequestMapping(value="/deploy/{fileName}", method= RequestMethod.GET)
    @ResponseBody
    public Result deploy(@PathVariable String fileName) {
        activityService.deploy(fileName);
        return resultWrapper.success("部署成功");
    }

    @RequestMapping(value="/list", method= RequestMethod.GET)
    public String list() {
        return "activity/process/list";
    }

    @RequestMapping(value="/history", method= RequestMethod.GET)
    public String history() {
        return "activity/process/history";
    }


    @RequestMapping(value="/start/{key}", method= RequestMethod.GET)
    public String start(@PathVariable String key) {
        return "activity/start";
    }

    @RequestMapping(value="/startForm/{processKey}", method= RequestMethod.POST)
    public String startForm(@PathVariable String processKey, HttpServletRequest request) {
        String userId = "Huang";//        request.getSession().getAttribute("userId");
        String processInstanceId = activityService.startForm(userId, processKey, request.getParameterMap());
        return "activity/task/list";
    }

    @RequestMapping(value="/getStartFormProperties/{processKey}", method= RequestMethod.GET)
    @ResponseBody
    public Result getStartFormProperties(@PathVariable String processKey) {
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionKey(processKey).latestVersion().singleResult();
        //获取开始表单
        if(processDefinition == null)
            return resultWrapper.error("null");
        StartFormData startFormData = formService.getStartFormData(processDefinition.getId());
        if(startFormData != null){
            return resultWrapper.success("",startFormData.getFormProperties());
        }
        return resultWrapper.error("null");
    }
}
