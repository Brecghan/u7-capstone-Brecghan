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


class EmployeesHome extends BindingClass {
    constructor() {
        super();

        this.bindClassMethods(['mount', 'clientLoaded'], this);

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

        this.clientLoaded();
    }

    async clientLoaded() {
        document.getElementById("employee-home-page").innerText = 'Employees';
        const teamList = this.client.getTeamList();
        
        this.addFieldsToPage();
        this.addButtonsToPage();        

    }

    addFieldsToPage() {
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

        fieldZoneContainer.appendChild(radioZone);
        
        const searchByIdField = document.createElement("input");
        searchByIdField.type = "text";
        searchByIdField.placeholder = "Enter ID Number";

        fieldZoneContainer.appendChild(searchByIdField);

        const searchByTeam = document.createElement("select");

        fieldZoneContainer.appendChild(searchByTeam);
    }

    addButtonsToPage() {
        const buttonZoneContainer = document.getElementById("button-zone");
        buttonZoneContainer.className = "selection-group";

        const viewAllButton = document.createElement("button");
        viewAllButton.innerText = "View All";
        viewAllButton.className = "button";
        viewAllButton.type = "submit";
        viewAllButton.name = "employees-btn";
        viewAllButton.classList.add("button");

        viewAllButton.addEventListener("click", () => {
            window.location.href="employeesHome.html"
        });

        buttonZoneContainer.appendChild(viewAllButton);

        const searchByIdButton = document.createElement("button");
        searchByIdButton.innerText = "Search by ID";
        searchByIdButton.className = "button";
        searchByIdButton.type = "submit";
        searchByIdButton.name = "employees-btn";
        searchByIdButton.classList.add("button");

        searchByIdButton.addEventListener("click", () => {
            window.location.href="trainingsHome.html"
        });

        buttonZoneContainer.appendChild(searchByIdButton);

        const searchByTeamButton = document.createElement("button");
        searchByTeamButton.innerText = "Search By Team";
        searchByTeamButton.className = "button";
        searchByTeamButton.type = "submit";
        searchByTeamButton.name = "employees-btn";
        searchByTeamButton.classList.add("button");

        searchByTeamButton.addEventListener("click", () => {
            window.location.href="trainingsHome.html"
        });

        buttonZoneContainer.appendChild(searchByTeamButton);
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
