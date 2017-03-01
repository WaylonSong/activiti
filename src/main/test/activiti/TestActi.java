package activiti;

import org.activiti.engine.*;
import org.activiti.engine.form.FormData;
import org.activiti.engine.form.StartFormData;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.test.ActivitiRule;
import org.junit.*;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by song on 2017/1/4.
 */
public class TestActi {
    @Rule
    public ActivitiRule activitiRule = new ActivitiRule();

    @org.junit.Test
    public void testDeploy(){
        ProcessEngine processEngine = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("activiti.cfg.xml").buildProcessEngine();
//        获取流程相关的服务
        RepositoryService repositoryService = processEngine.getRepositoryService();
        //部署相关的流程配置
        repositoryService.createDeployment()
                .addClasspathResource("processes/vocation.bpmn")
                .deploy();
    }

    @Test
    public void testFormStart(){
//        获取流程相关的服务
        ProcessEngine processEngine = activitiRule.getProcessEngine();
        RepositoryService repositoryService = processEngine.getRepositoryService();
        FormService formService = processEngine.getFormService();
        TaskService taskService = processEngine.getTaskService();
        IdentityService identityService = processEngine.getIdentityService();
        RuntimeService runtimeService = processEngine.getRuntimeService();

        identityService.setAuthenticatedUserId("hsk");

        Map<String, String> variableMap = new HashMap<String, String>();
        variableMap.put("days", "17");
        variableMap.put("description", "生病了");

        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionKey("vocation").latestVersion().singleResult();
        //获取开始表单
        //StartFormData startFormData = formService.getStartFormData(processDefinition.getId());
        ProcessInstance processInstance = formService.submitStartFormData(processDefinition.getId(), variableMap);
//        runtimeService.startProcessInstanceById();

        Task task = taskService.createTaskQuery().processDefinitionId(processDefinition.getId()).list().get(0);
        System.out.println();
    }

    @Test
    public void testFormData(){
//        获取流程相关的服务
        ProcessEngine processEngine = activitiRule.getProcessEngine();
        RepositoryService repositoryService = processEngine.getRepositoryService();
        RuntimeService runtimeService = processEngine.getRuntimeService();
        FormService formService = processEngine.getFormService();
        TaskService taskService = processEngine.getTaskService();

        String id = "vocation:3:77512";
        //startForm properties etc
        StartFormData startFormData = formService.getStartFormData(id);
        //不是外置表单 会返回null
        Object object = formService.getRenderedStartForm(id);

        assert (object == null);
        List<Task> taskList = taskService.createTaskQuery().processInstanceId("125005").list();
//        List<Task> taskList = taskService.createTaskQuery().taskCandidateOrAssigned();
        Task task = taskList.get(taskList.size()-1);
        //map1 == map2 task和processInstance的Variable是相同的  [task前驱的变量会自动过来]
        Map map2 = taskService.getVariables(task.getId());
        //获取startFormData
        Map map1 = runtimeService.getVariables(task.getProcessInstanceId());
        //此task需要填写的form
        FormData formData = formService.getTaskFormData(task.getId());
        System.out.println();
    }

    @Test
    public void testStartProcessByRuntime(){
        ProcessEngine processEngine = activitiRule.getProcessEngine();
        RepositoryService repositoryService = processEngine.getRepositoryService();
        RuntimeService runtimeService = processEngine.getRuntimeService();
        FormService formService = processEngine.getFormService();
        TaskService taskService = processEngine.getTaskService();

//        部署相关的流程配置
        repositoryService.createDeployment()
                .addClasspathResource("processes/leave.bpmn")
                .deploy();
        Map<String, Object> variableMap = new HashMap();
        variableMap.put("reason", "年假");
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("leave_process", variableMap);
        System.out.println(processInstance.getId()+" "+processInstance.getProcessDefinitionId());
        List<Task> tasks = taskService.createTaskQuery().taskCandidateUser("leader").list();
        for (Task task : tasks) {
            System.out.println("Task available: " + task.getName() + " " + task.getId()+" "+task.getProcessInstanceId());
            task.setAssignee("swl");
        }
    }

}