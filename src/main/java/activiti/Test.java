package activiti;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
//import scala.util.parsing.combinator.testing.Str;

/**
 * Created by song on 2016/12/16.
 */
public class Test {

    public void test(){
        ProcessEngine processEngine = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("activiti.cfg.xml").buildProcessEngine();

        // Get Activiti services
        //获取流程相关的服务
        RepositoryService repositoryService = processEngine.getRepositoryService();
        RuntimeService runtimeService = processEngine.getRuntimeService();

        // Deploy the process definition
        //部署相关的流程配置
        repositoryService.createDeployment()
                .addClasspathResource("test.bpmn")
                .deploy();



    }
}
