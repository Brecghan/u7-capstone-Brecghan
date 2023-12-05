import TrainingMatrixClient from '../api/trainingMatrixClient';
import Header from '../components/header';
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";

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


class Employee extends BindingClass {
    constructor() {
        super();

        this.bindClassMethods(['mount', 'clientLoaded', 'updateEmployee', 'submitUpdate', 'deactivateEmployee' ], this);

        // Create a new datastore with an initial "empty" state.
        this.dataStore = new DataStore(EMPTY_DATASTORE_STATE);
        this.header = new Header(this.dataStore);
        console.log("employeesHome constructor");
    }

    /**
     * Add the header to the page and loads the TrainingMatrixClient.
     */
    mount() {

        this.header.addHeaderToPage();
        this.client = new TrainingMatrixClient();

        document.getElementById('update-employee-btn').addEventListener('click', this.updateEmployee);
        document.getElementById('deactivate-employee-btn').addEventListener('click', this.deactivateEmployee);
        document.getElementById('submit-update-btn').addEventListener('click', this.submitUpdate);

        this.clientLoaded();
    }

    async clientLoaded() {
        const urlParams = new URLSearchParams(window.location.search);
        const employeeId = urlParams.get('id');
        const employee = await this.client.getEmployee(employeeId);
        const employeeTests = await this.client.getTestList("null", employeeId, "null");
        this.dataStore.set('employee', employee);
        this.dataStore.set('employeeTests', employeeTests);
        const teamList = this.client.getTeamList();
        this.dataStore.set('teamList',teamList);

        document.getElementById("employee-view-page").innerText = employee.employeeName;
        this.addFieldsToPage();
    }

    async addFieldsToPage() {
        const employee = this.dataStore.get('employee')
        const fieldZoneContainer1 = document.getElementById("field-zone1");
        fieldZoneContainer1.className = "display-group";


        const employeeIdField = document.createElement("text");
        employeeIdField.innerHTML = `Employee Id:` + `<br>` + employee.employeeId; 
        fieldZoneContainer1.appendChild(employeeIdField);

        const employeeTeamField = document.createElement("text");
        employeeTeamField.innerHTML = `Employee Team:` + `<br>` + employee.team; 
        fieldZoneContainer1.appendChild(employeeTeamField);

        const employeeisActiveField = document.createElement("text");
        employeeisActiveField.innerHTML = `Employee Active Status:` + `<br>` + employee.isActive; 
        fieldZoneContainer1.appendChild(employeeisActiveField);


        const fieldZoneContainer2 = document.getElementById("field-zone2");
        fieldZoneContainer2.className = "display-group";

        const employeeStartDateField = document.createElement("text");
        employeeStartDateField.innerHTML = `Employee Start Date:` + `<br>` + employee.startDate.toString().substring(0,10); 
        fieldZoneContainer2.appendChild(employeeStartDateField);

        const employeeTrainingStatusField = document.createElement("text");
        employeeTrainingStatusField.innerHTML = `Employee Training Status:` + `<br>` + employee.trainingStatus; 
        fieldZoneContainer2.appendChild(employeeTrainingStatusField);


        const fieldZoneContainer3 = document.getElementById("field-zone3");
        fieldZoneContainer3.className = "display-group";
        const trainingsTableHeaders = ["Training Taken", "Training Date"]
        const tblTrain = document.getElementById("trainings-table");
        let theadTrain = tblTrain.createTHead();
        let rowTrain = theadTrain.insertRow();
        trainingsTableHeaders.forEach(function (item, index) {
            let thTrain = document.createElement("th");
            thTrain.style.background = '#70AD47';
            let textTrain = document.createTextNode(item);
            thTrain.appendChild(textTrain);
            rowTrain.appendChild(thTrain);
        });

        let training;
        for (training of employee.trainingsTaken) {
            const trainingArray = training.toString().split(":")
            if (trainingArray.length > 2) {
                for (var i = 2; i < 3; i++) {
                    trainingArray[1] += ':' + trainingArray[i];
                }
            }
            let row = tblTrain.insertRow();
            let cell1 = row.insertCell();
            let text1 = document.createTextNode(trainingArray[0]);
            cell1.appendChild(text1);
            let cell2 = row.insertCell();
            let text2 = document.createTextNode(trainingArray[1]);
            cell2.appendChild(text2);
        }
        fieldZoneContainer3.appendChild(tblTrain);

        const testTableHeaders = ["Test for Training", "Test Pass Status"]
        const tblTest = document.getElementById("tests-table");
        let theadTest = tblTest.createTHead();
        let rowTest = theadTest.insertRow();
        testTableHeaders.forEach(function (item, index) {
            let thTest = document.createElement("th");
            thTest.style.background = '#70AD47';
            let textTest = document.createTextNode(item);
            thTest.appendChild(textTest);
            rowTest.appendChild(thTest);
        });


        const testList = this.dataStore.get('employeeTests')
        let test;
        for (test of testList) {
            const testArray = test.trainingId.toString().split(":");
            if (testArray.length > 2) {
                for (var i = 1; i < 3; i++) {
                    testArray[0] += ':' + testArray[i];
                }
            } else {
                for (var i = 1; i < 2; i++) {
                    testArray[0] += ':' + testArray[i];
                }
            }
            let row = tblTest.insertRow();
            let cell1 = row.insertCell();
            let text1 = document.createTextNode(testArray[0]);
            cell1.appendChild(text1);
            let cell2 = row.insertCell();
            let text2 = document.createTextNode(test.hasPassed);
            cell2.appendChild(text2);
        }
        fieldZoneContainer3.appendChild(tblTest);
    }

    updateEmployee(){
        var modal = document.getElementById("myModal");
        modal.style.display = "block";

        const teamList = this.dataStore.get('teamList');

        const searchByTeam = document.getElementById("employee-team-field");

        let selectTag = document.createElement('select');
        teamList.forEach(function(value, key) {
            let opt = document.createElement("option");
            opt.value = value; // the index
            opt.innerHTML = key;
            selectTag.append(opt);
        });
        searchByTeam.appendChild(selectTag);
    }
    
    async submitUpdate(){    
        var updateName = document.getElementById("employee-name").value;
        var updateTeam = document.getElementById('employee-team-field').children[1].value;

        const employee = this.dataStore.get('employee');
        const updatedEmployee = await this.client.updateEmployee(updateName, employee.employeeId, updateTeam, employee.isActive, null, null)    

        if (updatedEmployee != null) {
            window.location.href = `/employee.html?id=${updatedEmployee.employeeId}`;
        }
    }

    async deactivateEmployee(){    
        if (confirm("Are you sure you wish to Deactivate?")) {
        const employee = this.dataStore.get('employee');
        const deactiveEmployee = await this.client.deleteEmployee(employee.employeeId)    

        if (deactiveEmployee != null) {
            window.location.href = `/employee.html?id=${deactiveEmployee.employeeId}`;
        }
    }
    }
}


/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const employee = new Employee();
    employee.mount();
};

window.addEventListener('DOMContentLoaded', main);