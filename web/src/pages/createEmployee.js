import TrainingMatrixClient from '../api/trainingMatrixClient';
import Header from '../components/header';
import BindingClass from '../util/bindingClass';
import DataStore from '../util/DataStore';

/**
 * Logic needed for the create employee page of the website.
 */
class CreateEmployee extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['mount', 'submit', 'redirectToViewEmployee', 'addTeamSelect'], this);
        this.dataStore = new DataStore();
        this.dataStore.addChangeListener(this.redirectToViewEmployee);
        this.header = new Header(this.dataStore);
    }

    /**
     * Add the header to the page and load the TrainingMatrixClient.
     */
    mount() {
        document.getElementById('create').addEventListener('click', this.submit);

        this.header.addHeaderToPage();

        this.client = new TrainingMatrixClient();

        this.addTeamSelect();
    }

    /**
     * Method to run when the create employee submit button is pressed. Call the TrainingMatrixClient to create the
     * employee.
     */
    async submit(evt) {
        evt.preventDefault();

        const errorMessageDisplay = document.getElementById('error-message');
        errorMessageDisplay.innerText = ``;
        errorMessageDisplay.classList.add('hidden');

        const createButton = document.getElementById('create');
        const origButtonText = createButton.innerText;
        createButton.innerText = 'Loading...';

        const employeeName = document.getElementById('employee-name').value;
        const employeeId = document.getElementById('employee-id').value;
        var startDate = document.getElementById('startdate').value;
        startDate += 'Z[UTC]';
        const team = document.getElementById('employee-team-field').children[1].value;

        const employee = await this.client.createEmployee(employeeName, employeeId, team, startDate, (error) => {
            createButton.innerText = origButtonText;
            errorMessageDisplay.innerText = `Error: ${error.message}`;
            errorMessageDisplay.classList.remove('hidden');
        });
        this.dataStore.set('employee', employee);
        console.table(employee);
    }

    /**
     * When the employee is updated in the datastore, redirect to the view employee page.
     */
    async redirectToViewEmployee() {
        const employee = this.dataStore.get('employee');
        if (employee != null) {
            window.location.href = `/employee.html?id=${employee.employeeId}`;
        }
    }

    addTeamSelect() {
        const teamList = this.client.getTeamList();

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
}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const createEmployee = new CreateEmployee();
    createEmployee.mount();
};

window.addEventListener('DOMContentLoaded', main);
