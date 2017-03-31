package org.educama.acceptancetests.stepdefinitions;

import org.educama.acceptancetests.pages.TaskListPage;
import org.jbehave.core.annotations.BeforeScenario;
import org.springframework.web.client.RestTemplate;

/**
 * Global (reusable) step definitions.
 */
public class GlobalStepDefinitions {

    TaskListPage taskListPage;

    @BeforeScenario
    public void closeAllCasesBeforeScenario() {
        new RestTemplate().postForEntity("http://localhost:8091/educama/v1/database", null, null);
    }
}
