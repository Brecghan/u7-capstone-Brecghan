import TrainingMatrixClient from '../api/trainingMatrixClient';
import Header from '../components/header';
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";
import LoadingSpinner from '../components/LoadingSpinner';

/*
The code below this comment is equivalent to...
const EMPTY_DATASTORE_STATE = {
    'search-criteria': '',
    'search-results': [],
};

...but uses the "KEY" constants instead of "magic strings".
The "KEY" constants will be reused a few times below.
*/

const SEARCH_CRITERIA_KEY = 'search-criteria';
const SEARCH_RESULTS_KEY = 'search-results';
const EMPTY_DATASTORE_STATE = {
    [SEARCH_CRITERIA_KEY]: '',
    [SEARCH_RESULTS_KEY]: [],
};


class TestsHome extends BindingClass {
    constructor() {
        super();

        this.bindClassMethods(['mount', 'clientLoaded', 'getTests', 'redirectToTestView', 'addFieldsToPage', 'displayTests' ], this);

        // Create a new datastore with an initial "empty" state.
        this.dataStore = new DataStore(EMPTY_DATASTORE_STATE);
        this.header = new Header(this.dataStore);
        this.LoadingSpinner = new LoadingSpinner;
        console.log("trainingsHome constructor");
    }

    /**
     * Add the header to the page and loads the TrainingMatrixClient.
     */
    mount() {


        this.header.addHeaderToPage();
        this.client = new TrainingMatrixClient();

        document.getElementById('employee-ID-btn').addEventListener('click', this.getTests);
        document.getElementById('training-ID-btn').addEventListener('click', this.getTests);

        this.dataStore.set("isActive", "true");
        this.dataStore.set("trainingSeries", "null");
        this.LoadingSpinner.showLoadingSpinner("Loading Page");
        this.clientLoaded();
    }

    async clientLoaded() {
        document.getElementById("tests-home-page").innerText = 'Tests';
        
        this.addFieldsToPage();
    }

    async addFieldsToPage() {
        const fieldZoneContainer = document.getElementById("field-zone");
        fieldZoneContainer.className = "selection-group";
        
        const searchByEmployeeIdField = document.createElement("input");
        searchByEmployeeIdField.type = "text";
        searchByEmployeeIdField.id = "searchByEmployeeIdField";
        searchByEmployeeIdField.placeholder = "Enter Employee ID";
        searchByEmployeeIdField.size = 25;

        fieldZoneContainer.appendChild(searchByEmployeeIdField);

        const searchByTrainingIdField = document.createElement("input");
        searchByTrainingIdField.type = "text";
        searchByTrainingIdField.id = "searchByTrainingIdField";
        searchByTrainingIdField.placeholder = "Enter Training ID";
        searchByTrainingIdField.size = 25;

        fieldZoneContainer.appendChild(searchByTrainingIdField);
        this.LoadingSpinner.hideLoadingSpinner();
    }

    
    async getTests(){
        this.LoadingSpinner.showLoadingSpinner("Getting Tests");
        const employeeId = document.getElementById("searchByEmployeeIdField").value;
        const trainingId = document.getElementById("searchByTrainingIdField").value;
        console.log('employeeId= ' + employeeId);
        console.log('trainingId= ' + trainingId);
        var testsList;
        var idSearched;
        if (employeeId) {
            console.log('AM I HITTING THIS?employee');
            testsList = await this.client.getTestList(null, employeeId, null);
            idSearched = employeeId;
            this.displayTests(testsList);
        } if (trainingId) {
            testsList = await this.client.getTestList(trainingId, null, null);
            idSearched = trainingId;
            this.displayTests(testsList);
        } else if (!employeeId && !trainingId) {
            window.alert("Enter an ID to continue");
        }
        document.getElementById("searchByEmployeeIdField").value = '';
        document.getElementById("searchByTrainingIdField").value = '';
        console.log('testsList = ' + testsList)
        if (testsList.length === 0) {
            console.log('AM I HITTING THIS?length check')
            document.getElementById("tests-table").innerHTML = "No tests associated with ID: " + idSearched;
            this.LoadingSpinner.hideLoadingSpinner();
        }
    }

    async displayTests(testsList) {
        const aboveTestsDisplayZoneContainer = document.getElementById("above-tests-display-zone");
        aboveTestsDisplayZoneContainer.innerHTML = "Click a Test to View Details/Update";

        const testsDisplayZoneContainer = document.getElementById("tests-display-zone");
        testsDisplayZoneContainer.className = "tests-table";

        const tableHeaders = ["Training ID", "Employee Id", "Has Passed", "Score to Pass"]

        const tbl = document.getElementById("tests-table");
        while (tbl.firstChild) {
            tbl.removeChild(tbl.firstChild)
        }
        let thead = tbl.createTHead();
        let row = thead.insertRow();
        tableHeaders.forEach(function (item, index) {
            let th = document.createElement("th");
            let text = document.createTextNode(item);
            th.appendChild(text);
            row.appendChild(th);
        });

        let test;

        for (test of testsList) {
            let row = tbl.insertRow();
            row.style["color"] = "#00a5f9";
            row.style["text-decoration"] = "underline";
            row.style["cursor"] = "pointer";
            let cell1 = row.insertCell();
            let text1 = document.createTextNode(test.trainingId);
            cell1.appendChild(text1);
            let cell2 = row.insertCell();
            let text2 = document.createTextNode(test.employeeId);
            cell2.appendChild(text2);
            let cell3 = row.insertCell();
            let text3 = document.createTextNode(test.hasPassed);
            cell3.appendChild(text3);
            let cell4 = row.insertCell();
            let text4 = document.createTextNode(test.scoreToPass);
            cell4.appendChild(text4);
        }
        testsDisplayZoneContainer.appendChild(tbl);

        var table = document.getElementById("tests-table");
        var rows = table.getElementsByTagName("tr");
        for (var i = 0; i < rows.length; i++) {
           var currentRow = table.rows[i];
           var createClickHandler = function(row) {
              return function() {
                 var cell1 = row.getElementsByTagName("td")[0];
                 var cell2 = row.getElementsByTagName("td")[1];
                 if (cell1 && cell2) {
                    var trainingId = cell1.innerHTML;
                    var employeeId = cell2.innerHTML;
                    var testId = trainingId + '~' + employeeId;
                    window.location.href = `/test.html?id=${testId}`;
                 }
              };
           };
           currentRow.onclick = createClickHandler(currentRow);
        }
        this.LoadingSpinner.hideLoadingSpinner();
    }

    redirectToTestView(testId) {
        this.LoadingSpinner.showLoadingSpinner("Redirecting to Test");
        window.location.href = `/test.html?id=${testId}`;
    }
}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const testsHome = new TestsHome();
    testsHome.mount();
};

window.addEventListener('DOMContentLoaded', main);
