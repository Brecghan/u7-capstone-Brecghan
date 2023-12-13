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


class Training extends BindingClass {
    constructor() {
        super();

        this.bindClassMethods(['mount', 'clientLoaded', 'updateTraining', 'submitUpdate', 'deactivateTraining', 'createTests', 'submitTestCreate', 'addEmployees', 'addEmployeesModalShow' ], this);

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
        this.LoadingSpinner.showLoadingSpinner("Loading Training Info");
        document.getElementById('update-training-btn').addEventListener('click', this.updateTraining);
        document.getElementById('deactivate-training-btn').addEventListener('click', this.deactivateTraining);
        document.getElementById('submit-update-btn').addEventListener('click', this.submitUpdate);
        document.getElementById('create-tests-btn').addEventListener('click', this.createTests);
        document.getElementById('submit-test-create-btn').addEventListener('click', this.submitTestCreate);
        document.getElementById('add-employee-btn').addEventListener('click', this.addEmployeesModalShow);
        document.getElementById('submit-add-employees-btn').addEventListener('click', this.addEmployees);

        this.clientLoaded();
    }

    async clientLoaded() {
        const urlParams = new URLSearchParams(window.location.search);
        const trainingId = urlParams.get('id');
        const training = await this.client.getTraining(trainingId);
        const trainingTests = await this.client.getTestList(trainingId, "null", "null");
        if (training.testsForTraining.length === training.employeesTrained.length) {
            const testButton = document.getElementById('create-tests-btn');
            testButton.style.visibility = 'hidden';
        }
        this.dataStore.set('training', training);
        this.dataStore.set('trainingTests', trainingTests);
        const statusList = this.client.getStatusList();
        this.dataStore.set('statusList',statusList);

        document.getElementById("training-view-page").innerText = training.trainingName;
        this.addFieldsToPage();
        window.onclick = function(event) {
            if (event.target === document.getElementById("myModal")) {
                document.getElementById("myModal").style.display = "none";
                document.getElementById("training-status-field").removeChild(document.getElementById("training-status-field").lastChild)
            }
        }
    }

    async addFieldsToPage() {
        const training = this.dataStore.get('training')
        const fieldZoneContainer1 = document.getElementById("field-zone1");
        fieldZoneContainer1.className = "display-group";


        const employeeIdField = document.createElement("text");
        employeeIdField.innerHTML = `Training Id:` + `<br>` + training.trainingId; 
        fieldZoneContainer1.appendChild(employeeIdField);

        const employeeTeamField = document.createElement("text");
        employeeTeamField.innerHTML = `Months until Training Expires:` + `<br>` + training.monthsTilExpire; 
        fieldZoneContainer1.appendChild(employeeTeamField);

        const employeeisActiveField = document.createElement("text");
        employeeisActiveField.innerHTML = `Training Active Status:` + `<br>` + training.isActive; 
        fieldZoneContainer1.appendChild(employeeisActiveField);


        const fieldZoneContainer2 = document.getElementById("field-zone2");
        fieldZoneContainer2.className = "display-group";

        const employeeStartDateField = document.createElement("text");
        employeeStartDateField.innerHTML = `Training Date:` + `<br>` + training.trainingDate.toString().substring(0,10); 
        fieldZoneContainer2.appendChild(employeeStartDateField);

        const employeeTrainingStatusField = document.createElement("text");
        employeeTrainingStatusField.innerHTML = `Training Expiration Status:` + `<br>` + training.expirationStatus; 
        fieldZoneContainer2.appendChild(employeeTrainingStatusField);


        const fieldZoneContainer3 = document.getElementById("field-zone3");
        fieldZoneContainer3.className = "display-group";
        const employeesTableHeaders = ["Employees ID", "Employee Name"]
        const tblTrain = document.getElementById("trainings-table");
        let theadTrain = tblTrain.createTHead();
        let rowTrain = theadTrain.insertRow();
        employeesTableHeaders.forEach(function (item, index) {
            let thTrain = document.createElement("th");
            thTrain.style.background = '#121212';
            let textTrain = document.createTextNode(item);
            thTrain.appendChild(textTrain);
            rowTrain.appendChild(thTrain);
        });

        let employee;
        if (training.employeesTrained.length === 0) {
            console.log("SHOULD SHOW EMPTY TABLE");
            document.getElementById("trainings-table").innerHTML = 'No employees have been added to the training';
        } else {
        for (employee of training.employeesTrained) {
            const employeeInfo = await this.client.getEmployee(employee);
            let row = tblTrain.insertRow();
            let cell1 = row.insertCell();
            let text1 = document.createTextNode(employeeInfo.employeeId);
            cell1.appendChild(text1);
            let cell2 = row.insertCell();
            let text2 = document.createTextNode(employeeInfo.employeeName);
            cell2.appendChild(text2);
        }
        fieldZoneContainer3.appendChild(tblTrain);
    }

        const testTableHeaders = ["Employee Id", "Test Pass Status"]
        const tblTest = document.getElementById("tests-table");
        let theadTest = tblTest.createTHead();
        let rowTest = theadTest.insertRow();
        testTableHeaders.forEach(function (item, index) {
            let thTest = document.createElement("th");
            thTest.style.background = '#121212';
            let textTest = document.createTextNode(item);
            thTest.appendChild(textTest);
            rowTest.appendChild(thTest);
        });


        const testList = this.dataStore.get('trainingTests')
        let test;
        if (testList.length === 0) {
            console.log("SHOULD SHOW EMPTY Test TABLE");
            fieldZoneContainer3.appendChild(tblTest);
            document.getElementById("tests-table").innerHTML = 'No tests have been created for this training';
        } else {
        for (test of testList) {
            let row = tblTest.insertRow();
            row.style["color"] = "#00a5f9";
            row.style["text-decoration"] = "underline";
            row.style["cursor"] = "pointer";
            let cell1 = row.insertCell();
            let text1 = document.createTextNode(test.employeeId);
            cell1.appendChild(text1);
            let cell2 = row.insertCell();
            let text2 = document.createTextNode(test.hasPassed);
            cell2.appendChild(text2);
        }
        fieldZoneContainer3.appendChild(tblTest);

        var table = document.getElementById("tests-table");
        var rows = table.getElementsByTagName("tr");
        for (var i = 0; i < rows.length; i++) {
           var currentRow = table.rows[i];
           var createClickHandler = function(row) {
              return function() {
                 var cell1 = row.getElementsByTagName("td")[0];
                 var cell2 = row.getElementsByTagName("td")[1];
                 if (cell1 && cell2) {
                    var trainingId = training.trainingId;
                    var employeeId = cell1.innerHTML;
                    var testId = trainingId + '~' + employeeId;
                    window.location.href = `/test.html?id=${testId}`;
                 }
              };
           };
           currentRow.onclick = createClickHandler(currentRow);
            }
            }
            this.LoadingSpinner.hideLoadingSpinner();
        }

    updateTraining(){
        var modal = document.getElementById("myModal");
        modal.style.display = "block";

        const statusList = this.dataStore.get('statusList');
        const training = this.dataStore.get('training');

        const searchByTeam = document.getElementById("training-status-field");

        let selectTag = document.createElement('select');
        statusList.forEach(function(value, key) {
            let opt = document.createElement("option");
            opt.id = 'trainingSelectOptions';
            opt.value = key; // the index
            opt.innerHTML = value;
            selectTag.append(opt);
        });
        searchByTeam.appendChild(selectTag);
        console.table(training);
        document.getElementById("trainingSelectOptions").value = training.expirationStatus;
        document.getElementById("trainingSelectOptions").innerHTML = training.expirationStatus;
        document.getElementById("months-til").value = training.monthsTilExpire;

    }
    
    async submitUpdate(){    
        this.LoadingSpinner.showLoadingSpinner("Updating Training Info");
        var updateMonths = document.getElementById("months-til").value;
        var updateExpiration = document.getElementById('training-status-field').children[1].value;
        console.log('updateMonths= '+ updateMonths);
        console.log('updateExpiration= '+ updateExpiration);
        if (updateMonths === '' || updateExpiration === 'null') {
            alert("Please populate both fields when updating");
        } else {

        const training = this.dataStore.get('training');
        const updatedTraining = await this.client.updateTraining(training.trainingId, training.isActive, updateMonths, null, null, updateExpiration)    

        if (updatedTraining != null) {
            window.location.href = `/training.html?id=${updatedTraining.trainingId}`;
        }
    }
    }

    async deactivateTraining(){    
        if (confirm("Are you sure you wish to Deactivate?")) {
        this.LoadingSpinner.showLoadingSpinner("Deactivating Training");
        const training = this.dataStore.get('training');
        const deactiveTraining = await this.client.deleteTraining(training.trainingId)    

        if (deactiveTraining != null) {
            window.location.href = `/training.html?id=${deactiveTraining.trainingId}`;
        }
    }
    }

    createTests(){
        var modal = document.getElementById("myModal2");
        modal.style.display = "block";
        const testList = this.dataStore.get('trainingTests')
        if (testList.length != 0) {
            var test = testList[0]
            document.getElementById('score-to-pass').value = test.scoreToPass;
        }
    }

    async submitTestCreate(){
        this.LoadingSpinner.showLoadingSpinner("Creating Tests for Training");
        var scoreToPass = document.getElementById('score-to-pass').value;
        const training = this.dataStore.get('training');
        var newTestsNeeded = [];
        var employeesWithTests = [];
        var testList = this.dataStore.get("trainingTests");
        let test;
        for (test of testList) {
            employeesWithTests.push(test.employeeId);
        }
        let employee;
        for (employee of training.employeesTrained){
            if (!employeesWithTests.includes(employee)) {
                newTestsNeeded.push(employee);
            }
        }
        const updatedTraining = await this.client.createTest(training.trainingId, newTestsNeeded, scoreToPass)    
       
        if (updatedTraining != null) {
            window.location.href = `/training.html?id=${training.trainingId}`;
        }
    }

    async addEmployeesModalShow() {
        var modal = document.getElementById("myModal3");
        modal.style.display = "block";
        const employeesList = document.getElementById("employeeList");

        const employees = await this.client.getEmployeeList(null, true);
        let selectTag = document.createElement('select');
        selectTag.id = 'employeeSelection';
        selectTag.multiple = true;
        selectTag.size = employees.length;
        employees.forEach(employee => {
            let opt = document.createElement("option");
            opt.value = employee.employeeId; // the index
            opt.innerHTML = employee.employeeName;
            selectTag.append(opt);
        });
        employeesList.appendChild(selectTag);

    }
    
    async addEmployees() {
        this.LoadingSpinner.showLoadingSpinner("Adding Employees to Training");
        const employeeList = document.querySelectorAll('#employeeSelection option:checked');
        const values = Array.from(employeeList).map(el => el.value);
        const training = this.dataStore.get('training');
        const trainingReturn = await this.client.updateTraining(training.trainingId, null, null, values, null, null)
        if (trainingReturn != null) {
            window.location.href = `/training.html?id=${trainingReturn.trainingId}`;
        }
    
    }

}


/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const training = new Training();
    training.mount();
};

window.addEventListener('DOMContentLoaded', main);