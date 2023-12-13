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


class Test extends BindingClass {
    constructor() {
        super();

        this.bindClassMethods(['mount', 'clientLoaded', 'updateTest', 'submitUpdate' ], this);

        // Create a new datastore with an initial "empty" state.
        this.dataStore = new DataStore(EMPTY_DATASTORE_STATE);
        this.header = new Header(this.dataStore);
        this.LoadingSpinner = new LoadingSpinner;
        console.log("trainingView constructor");
    }

    /**
     * Add the header to the page and loads the TrainingMatrixClient.
     */
    mount() {

        this.header.addHeaderToPage();
        this.client = new TrainingMatrixClient();
        this.LoadingSpinner.showLoadingSpinner("Loading Test Info");
        document.getElementById('update-test-btn').addEventListener('click', this.updateTest);
        document.getElementById('submit-update-btn').addEventListener('click', this.submitUpdate);


        this.clientLoaded();
    }

    async clientLoaded() {
        const urlParams = new URLSearchParams(window.location.search);
        const testId = urlParams.get('id');
        const test = await this.client.getTest(testId);
        this.dataStore.set('test', test);
        this.dataStore.set('testAttempts', test.testAttempts);
        const employee = await this.client.getEmployee(test.employeeId);
        const userName = employee.employeeName;

        document.getElementById("test-view-page").innerText = `Viewing` + `\n` + userName + `\n` + `Test`;
       
        this.addFieldsToPage();
    }

    async addFieldsToPage() {
        const test = this.dataStore.get('test')
        const fieldZoneContainer1 = document.getElementById("field-zone1");
        fieldZoneContainer1.className = "display-group";


        const employeeIdField = document.createElement("text");
        employeeIdField.innerHTML = `Training Id:` + `<br>` + test.trainingId; 
        fieldZoneContainer1.appendChild(employeeIdField);

        const employeeTeamField = document.createElement("text");
        employeeTeamField.innerHTML = `Employee Id:` + `<br>` + test.employeeId; 
        fieldZoneContainer1.appendChild(employeeTeamField);

        const employeeisActiveField = document.createElement("text");
        employeeisActiveField.innerHTML = `Test Passing Status:` + `<br>` + test.hasPassed; 
        fieldZoneContainer1.appendChild(employeeisActiveField);


        const fieldZoneContainer2 = document.getElementById("field-zone2");
        fieldZoneContainer2.className = "display-group";

        const employeeStartDateField = document.createElement("text");
        employeeStartDateField.innerHTML = `Score to Pass:` + `<br>` + test.scoreToPass; 
        fieldZoneContainer2.appendChild(employeeStartDateField);

        const employeeTrainingStatusField = document.createElement("text");
        employeeTrainingStatusField.innerHTML = `Latest Test Attempt Score:` + `<br>` + test.latestScore; 
        fieldZoneContainer2.appendChild(employeeTrainingStatusField);


        const fieldZoneContainer3 = document.getElementById("field-zone3");
        fieldZoneContainer3.className = "display-group";
        const testTableHeaders = ["Test Attempt History"]
        const tblTest = document.getElementById("attempts-table");
        let theadTest = tblTest.createTHead();
        let rowTest = theadTest.insertRow();
        testTableHeaders.forEach(function (item, index) {
            let thTest = document.createElement("th");
            thTest.style.background = '#121212';
            let textTest = document.createTextNode(item);
            thTest.appendChild(textTest);
            rowTest.appendChild(thTest);
        });


        const testList = this.dataStore.get('testAttempts')
        if (testList.length === 0) {
            console.log("SHOULD SHOW EMPTY TABLE");
            document.getElementById("attempts-table").innerHTML = 'No test attempts have been taken';
        } else {
        let testAttempt;
        for (testAttempt of testList) {
            let row = tblTest.insertRow();
            let cell1 = row.insertCell();
            let text1 = document.createTextNode(testAttempt);
            cell1.appendChild(text1);
        }
        fieldZoneContainer3.appendChild(tblTest);
    }
    this.LoadingSpinner.hideLoadingSpinner();
    }

    updateTest(){
        var modal = document.getElementById("myModal");
        modal.style.display = "block";

    }
    
    async submitUpdate(){    
        this.LoadingSpinner.showLoadingSpinner("Updating Test Info");
        var latestScore = document.getElementById("latest-score").value;
      
        const test = this.dataStore.get('test');
        const updatedTest = await this.client.updateTest(test.trainingId, test.employeeId, latestScore)    

        if (updatedTest != null) {
            window.location.href = `/test.html?id=${updatedTest.trainingId + "~" + updatedTest.employeeId}`;
        }
    }

}


/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const test = new Test();
    test.mount();
};

window.addEventListener('DOMContentLoaded', main);