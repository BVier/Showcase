package org.educama.acceptancetests.stepdefinitions;

import net.thucydides.core.annotations.Steps;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.ProcessEngineConfiguration;
import org.camunda.bpm.engine.task.Task;
import org.educama.acceptancetests.steps.ShipmentsListSteps;
import org.educama.acceptancetests.steps.TaskListSteps;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import java.util.List;

/**
 * Step Definitions for the task list.
 */

public class TaskListStepDefinitions {
    @Steps
    TaskListSteps user;

    @Steps
    ShipmentsListSteps shipmentsListSteps;

    ProcessEngine processEngine = ProcessEngineConfiguration.createStandaloneInMemProcessEngineConfiguration()
            .setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_FALSE)
            .setJdbcUrl("jdbc:h2:tcp://127.0.1.1:12345/mem:testdb")
            .setJobExecutorActivate(true)
            .buildProcessEngine();

    @Given(value = "there are $count tasks with name '$description' assigned to $assignee")
    public void givenThereAreNumberTasksWithNameCheckShipmentAssignedToName(int count, String description, String assignee) {
        if ("Tom".equals(assignee)) {
            shipmentsListSteps.openShipmentsListPage();
            for (int i = 0; i < count; i++) {
                shipmentsListSteps.addOneShipment();
            }
        } else {
            boolean userExists = processEngine.getIdentityService().createUserQuery().userId(assignee).list().isEmpty();
            if (!userExists) {
                processEngine.getIdentityService().newUser(assignee);
            }
            List<Task> oldTasks = processEngine.getTaskService().createTaskQuery().active().list();
            shipmentsListSteps.openShipmentsListPage();
            for (int i = 0; i < count; i++) {
                shipmentsListSteps.addOneShipment();
            }
            // !!! DOES NOT WORK !!!!!!!    
            List<Task> newTasks = processEngine.getTaskService().createTaskQuery().active().list();
            for (Task task : newTasks) {
                if (!oldTasks.contains(task)) {
                    task.delegate(assignee);
                }
            }
        }
        // TODO Create Task/Shipments for other assignees
    }

    @When("Tom requests the list of tasks")
    public void whenTomRequestsTheListOfTasks() {
        user.openTaskListPage();
    }

    @Then("Tom sees $count tasks with name 'Check shipment'")
    public void thenISeeTheTasksInTheTasklist(int count) {
        user.checksTheTaskList(count);
    }

    @Then("Tom sees an empty list")
    public void thenISeeAnEmptyList() {
        user.checksTheTaskList(0);
    }

}

