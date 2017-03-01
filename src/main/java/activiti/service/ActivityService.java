package activiti.service;

import org.activiti.engine.*;
import org.activiti.engine.form.StartFormData;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Service
public class ActivityService {
    @Resource
    IdentityService identityService;

    @Resource
    RuntimeService runtimeService;

    @Resource
    TaskService taskService;

    @Resource
    RepositoryService repositoryService;

    @Resource
    FormService formService;

    @Transactional
    public  void deploy(String processFileName){
        //部署相关的流程配置
        repositoryService.createDeployment()
//                .addClasspathResource("processes/vocation.bpmn.xml")
                .addClasspathResource("processes/"+processFileName)
                .deploy();
    }

	@Transactional
    public List<Task> getTasks(String assignee) {
        return taskService.createTaskQuery().taskAssignee(assignee).list();
    }

    @Transactional
    public Task getTaskById(String id) {
        runtimeService.createProcessInstanceQuery().deploymentId(id).active();
        return null;
    }
    @Transactional
    public void startProcessByKey(String key) {
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionKey(key).latestVersion().singleResult();
        startProcessByRuntime(processDefinition.getId());
    }

    @Transactional
    private void startProcessByRuntime(String id) {
        runtimeService.startProcessInstanceById("id");
    }

    public String startForm(String userId, String key, Map map) {
        identityService.setAuthenticatedUserId(userId);
        Map<String, String> variableMap = new HashMap<String, String>();
        for(Iterator iter = map.entrySet().iterator(); iter.hasNext();){
            Map.Entry element = (Map.Entry)iter.next();
            String strKey = (String)element.getKey();
            String[] strObj = (String[])element.getValue();
            System.out.println();
            variableMap.put(strKey, strObj[0]);
        }
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionKey(key).latestVersion().singleResult();
        //获取开始表单
        ProcessInstance processInstance = formService.submitStartFormData(processDefinition.getId(), variableMap);
        return processInstance.getId();
    }

    public void complete(String id, Map parameterMap) {
        try {
            taskService.setVariable(id, "isApproved", "true");
            //是指多个candidate的时候,该用户接受了请求,其他用户就不可见了
//        taskService.claim(id, "swl");
            taskService.complete(id);

        }catch(ActivitiObjectNotFoundException exception){
            exception.printStackTrace();
        }
    }

    public void viewTask(String id) {
        //starter
        Map map = taskService.getVariables(id);
//        taskService.
    }
}