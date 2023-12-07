package com.atguigu.auth.activit;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class ProcessTest {

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private HistoryService historyService;

    // 查询个人的代办任务 - zhangsan
    @Test
    public void findTaskList() {
        String assign = "耶律大石";
        List<Task> list = taskService.createTaskQuery().taskAssignee(assign).list();
        for (Task task: list) {
            // 流程实例id：一个流程只有一个，标识这个流程
            System.out.println("流程实例id："+task.getProcessInstanceId());
            // 任务id：流程每进行到某个节点就会给这个节点分配一个任务id
            System.out.println("任务id："+task.getId());
            System.out.println("任务负责人："+task.getAssignee());
            System.out.println("任务名称："+task.getName());
        }
    }

    // 查询已经处理的任务
    @Test
    public void findCompleteTaskList() {
        List<HistoricTaskInstance> list = historyService.createHistoricTaskInstanceQuery()
                .taskAssignee("耶律大石").finished().list();

        for (HistoricTaskInstance historicTaskInstance : list) {
            System.out.println("实例id："+historicTaskInstance.getProcessInstanceId());
            System.out.println("任务id："+historicTaskInstance.getId());
            System.out.println("任务负责人："+historicTaskInstance.getAssignee());
            System.out.println("任务名称："+historicTaskInstance.getName());
        }
    }

    // 处理当前任务
    @Test
    public void completeTask() {
        // 查询负责人需要处理任务，返回一条
        Task task = taskService.createTaskQuery().taskAssignee("耶律大石").singleResult();
        // 完成任务，参数：任务id
        // 完成任务后，自动到下一个节点
        taskService.complete(task.getId());
    }

    // 启动流程实例
    @Test
    public void startProcess() {
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("process");
        System.out.println("流程定义id：" + processInstance.getProcessDefinitionId());
        System.out.println("流程实例" + processInstance.getId());
        System.out.println("流程活动id" + processInstance.getActivityId());
    }

    // 单个文件部署
    @Test
    public void deployProcess() {
        // 流程部署
        Deployment deploy = repositoryService.createDeployment()
                .addClasspathResource("process/qingjia.bpmn20.xml")
                .name("请假申请流程")
                .deploy();
        System.out.println(deploy.getId());
        System.out.println(deploy.getName());
        System.out.println(deploy.getCategory());
    }

}
