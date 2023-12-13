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


class EmployeesHome extends BindingClass {
    constructor() {
        super();

        this.bindClassMethods(['mount', 'clientLoaded', 'addFieldsToPage', 'getEmployees', 'displayEmployees', 'redirectToEmployeeView'], this);

        // Create a new datastore with an initial "empty" state.
        this.dataStore = new DataStore(EMPTY_DATASTORE_STATE);
        this.header = new Header(this.dataStore);
        this.LoadingSpinner = new LoadingSpinner;
        console.log("employeesHome constructor");
    }

    /**
     * Add the header to the page and loads the TrainingMatrixClient.
     */
    mount() {


        this.header.addHeaderToPage();
        this.client = new TrainingMatrixClient();
        this.LoadingSpinner.showLoadingSpinner("Loading Page");
        this.dataStore.set("isActive", "true");
        this.dataStore.set("team", "null");

        document.getElementById('employees-btn').addEventListener('click', this.getEmployees);
        document.getElementById('employee-ID-btn').addEventListener('click', this.redirectToEmployeeView);
        document.getElementById('Team-Search-btn').addEventListener('click', () => {
            this.dataStore.set("team", document.getElementById('teamListSelectDropDown').value);
            this.getEmployees();
        });

        this.clientLoaded();
    }

    async clientLoaded() {
        document.getElementById("employee-home-page").innerText = 'Employees';
        this.addFieldsToPage();
    }

    async addFieldsToPage() {
        const fieldZoneContainer = document.getElementById("field-zone");
        fieldZoneContainer.className = "selection-group";
        
        const radioZone = document.createElement("div");

        const viewAllRadio = document.createElement("input");
        viewAllRadio.setAttribute("type", "radio");
        viewAllRadio.setAttribute("id", "viewAllRadioSelector");

        const viewAllRadioLabel = document.createElement("label");
        const viewAllRadioTextNode = document.createTextNode("Label text");
        viewAllRadioTextNode.textContent = "Show Inactive Employees";
        viewAllRadioLabel.setAttribute("for", "viewAllRadioSelector");
        radioZone.appendChild(viewAllRadio);
        radioZone.appendChild(viewAllRadioTextNode);

        viewAllRadio.addEventListener("click", () => {
            this.dataStore.set("isActive", "false");
        });

        fieldZoneContainer.appendChild(radioZone);
        
        const searchByIdField = document.createElement("input");
        searchByIdField.type = "text";
        searchByIdField.placeholder = "Enter ID Number";
        searchByIdField.size = 25;

        fieldZoneContainer.appendChild(searchByIdField);

        const teamList = this.client.getTeamList();

        const searchByTeam = document.createElement("select");
        searchByTeam.id = 'teamListSelectDropDown';

        let optionTeamList = searchByTeam.options;
        optionTeamList.length = 0;

        teamList.forEach(function(value, key) {
            optionTeamList.add(
            new Option(value, key)
          )});

        fieldZoneContainer.appendChild(searchByTeam);
        this.LoadingSpinner.hideLoadingSpinner();
    }

    async getEmployees(){
        this.LoadingSpinner.showLoadingSpinner("Loading Employees");
        const team = this.dataStore.get("team");
        const isActive = this.dataStore.get("isActive");
        const employeeList = await this.client.getEmployeeList(team, isActive);
        this.displayEmployees(employeeList);
    }

    async displayEmployees(employeeList) {
        const aboveEmployeesDisplayZoneContainer = document.getElementById("above-employees-display-zone");
        aboveEmployeesDisplayZoneContainer.innerHTML = "Click an Employee to View Details/Update";

        const employeesDisplayZoneContainer = document.getElementById("employees-display-zone");
        employeesDisplayZoneContainer.className = "employee-table";

        const tableHeaders = ["Employee Id", "Employee Name", "Employee Team", "Training Status", "Active Status"]

        const tbl = document.getElementById("employees-table");
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

        const teamChosen = this.dataStore.get("team");
        let employee;
        if (employeeList.length === 0){
            document.getElementById("employees-table").innerHTML = "No employees assigned to this team: " + teamChosen;
        } else {
        for (employee of employeeList) {
            let row = tbl.insertRow();
            row.style["color"] = "#00a5f9";
            row.style["text-decoration"] = "underline";
            row.style["cursor"] = "pointer";
            let cell1 = row.insertCell();
            let text1 = document.createTextNode(employee.employeeId);
            cell1.appendChild(text1);
            let cell2 = row.insertCell();
            let text2 = document.createTextNode(employee.employeeName);
            cell2.appendChild(text2);
            let cell3 = row.insertCell();
            let text3 = document.createTextNode(employee.team);
            cell3.appendChild(text3);
            let cell4 = row.insertCell();
            let text4 = document.createTextNode(employee.trainingStatus);
            cell4.appendChild(text4);
            let cell5 = row.insertCell();
            let text5 = document.createTextNode(employee.isActive);
            cell5.appendChild(text5);
        }
        employeesDisplayZoneContainer.appendChild(tbl);

        
        var table = document.getElementById("employees-table");
        var rows = table.getElementsByTagName("tr");
        for (var i = 0; i < rows.length; i++) {
           var currentRow = table.rows[i];
           var createClickHandler = function(row) {
              return function() {
                 var cell1 = row.getElementsByTagName("td")[0];
                 if (cell1) {
                    var employeeId = cell1.innerHTML;
                    window.location.href = `/employee.html?id=${employeeId}`
                 }
              };
           };
           currentRow.onclick = createClickHandler(currentRow);
        }
    }
    document.getElementById("teamListSelectDropDown").selectedIndex = 0;
    this.dataStore.set("team", 'null');
    this.LoadingSpinner.hideLoadingSpinner();
    }

    async redirectToEmployeeView() {
        this.LoadingSpinner.showLoadingSpinner("Redirecting to Employee");
        const employeeId = document.getElementById('field-zone').children[1].value;
        window.location.href = `/employee.html?id=${employeeId}`;
    }
}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const employeesHome = new EmployeesHome();
    employeesHome.mount();
};

window.addEventListener('DOMContentLoaded', main);
