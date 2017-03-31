package org.educama.acceptancetests.stepdefinitions;

import net.thucydides.core.annotations.Steps;
import org.educama.acceptancetests.steps.ShipmentsListSteps;
import org.educama.acceptancetests.steps.TaskListSteps;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

/**
 * Step Definitions for the task list.
 */

public class TaskListStepDefinitions {
    @Steps
    TaskListSteps user;

    @Steps
    ShipmentsListSteps shipmentsListSteps;

    @Given(value = "there are $count tasks with name '$description' assigned to $assignee")
    public void givenThereAreNumberTasksWithNameCheckShipmentAssignedToName(int count, String description, String assignee) {
        if ("Tom".equals(assignee)) {
            shipmentsListSteps.openShipmentsListPage();
            for (int i = 0; i < count; i++) {
                shipmentsListSteps.addOneShipment();
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

