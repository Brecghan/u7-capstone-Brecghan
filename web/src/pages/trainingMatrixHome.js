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


class TrainingMatrixHome extends BindingClass {
    constructor() {
        super();

        this.bindClassMethods(['mount', 'clientLoaded'], this);

        // Create a new datastore with an initial "empty" state.
        this.dataStore = new DataStore(EMPTY_DATASTORE_STATE);
        this.header = new Header(this.dataStore);
        console.log("trainingMatrixHome constructor");
    }

    /**
     * Add the header to the page and loads the TrainingMatrixClient.
     */
    mount() {


        this.header.addHeaderToPage();
        this.client = new TrainingMatrixClient();

        this.clientLoaded();
    }

    async clientLoaded() {
        const user = (await this.client.getIdentity());

        if (user == undefined){
            document.getElementById("home-page").innerText = 'Welcome! Please sign in before continuing.';
        } else {
            const username = (await this.client.authenticator.getCurrentUserInfo()).name;
            document.getElementById("home-page").innerText = 'Welcome, ' + username + '!';
            
            const buttonZoneContainer = document.getElementById("button-zone");
            buttonZoneContainer.className = "selection-group";
            
            const employeeButton = document.createElement("button");
            employeeButton.innerText = "Employees";
            employeeButton.className = "button";
            employeeButton.type = "submit";
            employeeButton.name = "employees-btn";
            employeeButton.classList.add("button");

            employeeButton.addEventListener("click", () => {
                window.location.href="employeesHome.html"
            });

            buttonZoneContainer.appendChild(employeeButton);

            const trainingButton = document.createElement("button");
            trainingButton.innerText = "Trainings";
            trainingButton.className = "button";
            trainingButton.type = "submit";
            trainingButton.name = "employees-btn";
            trainingButton.classList.add("button");

            trainingButton.addEventListener("click", () => {
                window.location.href="trainingsHome.html"
            });

            buttonZoneContainer.appendChild(trainingButton);
        }

    }

}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const trainingMatrixHome = new TrainingMatrixHome();
    trainingMatrixHome.mount();
};

window.addEventListener('DOMContentLoaded', main);