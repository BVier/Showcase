package org.educama.shipment.api;

import org.camunda.bpm.engine.CaseService;
import org.camunda.bpm.engine.runtime.CaseInstance;
import org.educama.shipment.boundary.ShipmentBoundaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST-Service to delete Cases in the database.
 */
@RestController
@RequestMapping(path = DatabaseController.DATABASE_CONTROL_PATH)
public class DatabaseController {

    public static final String DATABASE_CONTROL_PATH = "/educama/v1/database";

    @Autowired
    private ShipmentBoundaryService shipmentBoundaryService;

    @Autowired
    private CaseService caseService;

    /**
     * API call to delete all tasks and shipments.
     */
    @RequestMapping(method = RequestMethod.POST)
    public void closeCases() {
        // close all active cases. This includes all tasks and shipments.
        List<CaseInstance> allActiveCases = caseService.createCaseInstanceQuery().active().list();
        for (CaseInstance caseInstance : allActiveCases) {
            caseService.terminateCaseExecution(caseInstance.getId());
        }
    }
}
