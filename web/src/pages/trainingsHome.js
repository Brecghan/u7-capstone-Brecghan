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


class TrainingsHome extends BindingClass {
    constructor() {
        super();

        this.bindClassMethods(['mount', 'clientLoaded'], this);

        // Create a new datastore with an initial "empty" state.
        this.dataStore = new DataStore(EMPTY_DATASTORE_STATE);
        this.header = new Header(this.dataStore);
        console.log("trainingsHome constructor");
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
           
            const myContainer = document.getElementById("button-zone");
            myContainer.className = "selection-group";
           
            const button = document.createElement("button");
            button.innerText = "Employees";
            button.className = "button";
            button.type = "submit";
            button.name = "employees-btn";
            button.classList.add("button");

            button.addEventListener("click", () => {
                window.location.href="trainingsHome.html"
            });

            myContainer.appendChild(button);

            const button2 = document.createElement("button");
            button2.innerText = "Trainings";
            button2.className = "button";
            button2.type = "submit";
            button2.name = "employees-btn";
            button2.classList.add("button");

            button2.addEventListener("click", () => {
                window.location.href="trainingsHome.html"
            });

            myContainer.appendChild(button2);
        }

    }

}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const trainingsHome = new TrainingsHome();
    trainingsHome.mount();
};

window.addEventListener('DOMContentLoaded', main);
