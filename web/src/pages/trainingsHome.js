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


class TrainingsHome extends BindingClass {
    constructor() {
        super();

        this.bindClassMethods(['mount', 'clientLoaded', 'getTrainings', 'redirectToTrainingView', 'addFieldsToPage', 'displayTrainings'], this);

        // Create a new datastore with an initial "empty" state.
        this.dataStore = new DataStore(EMPTY_DATASTORE_STATE);
        this.header = new Header(this.dataStore);
        this.LoadingSpinner = new LoadingSpinner;
    }

    /**
     * Add the header to the page and loads the TrainingMatrixClient.
     */
    mount() {


        this.header.addHeaderToPage();
        this.client = new TrainingMatrixClient();
        this.LoadingSpinner.showLoadingSpinner("Loading Page");
        document.getElementById('trainings-btn').addEventListener('click', this.getTrainings);
        document.getElementById('training-ID-btn').addEventListener('click', this.redirectToTrainingView);
        document.getElementById('TrainingSeries-Search-btn').addEventListener("click", () => {
            this.dataStore.set("trainingSeries", document.getElementById('trainingSelectDropDown').value);
            this.getTrainings();
        });

        this.dataStore.set("isActive", "true");
        this.dataStore.set("trainingSeries", "null");

        this.clientLoaded();
    }

    async clientLoaded() {
        document.getElementById("trainings-home-page").innerText = 'Trainings';
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
        viewAllRadioTextNode.textContent = "Show Inactive Trainings";
        viewAllRadioLabel.setAttribute("for", "viewAllRadioSelector");
        radioZone.appendChild(viewAllRadio);
        radioZone.appendChild(viewAllRadioTextNode);

        viewAllRadio.addEventListener("click", () => {
            this.dataStore.set("isActive", "false");
        });

        fieldZoneContainer.appendChild(radioZone);

        const searchByIdField = document.createElement("input");
        searchByIdField.type = "text";
        searchByIdField.placeholder = "Enter Training ID";
        searchByIdField.size = 25;

        fieldZoneContainer.appendChild(searchByIdField);

        const trainingSeriesList = await this.client.getTrainingSeries();

        const searchByTraining = document.createElement("select");
        searchByTraining.id = 'trainingSelectDropDown';

        let optionTrainingList = searchByTraining.options;
        optionTrainingList.length = 0;

        let opt = document.createElement("option");
        opt.value = null; // the index
        opt.innerHTML = 'Select Series';
        optionTrainingList.add(opt);

        trainingSeriesList.forEach(tsl =>
            optionTrainingList.add(
                new Option(tsl.trainingSeriesName, tsl.trainingSeriesName)
            ));

        fieldZoneContainer.appendChild(searchByTraining);
        this.LoadingSpinner.hideLoadingSpinner();
    }


    async getTrainings() {
        this.LoadingSpinner.showLoadingSpinner("Loading Trainings");
        const trainingSeries = this.dataStore.get("trainingSeries");
        const isActive = this.dataStore.get("isActive");
        const trainingsList = await this.client.getTrainingList(trainingSeries, isActive, null);
        this.displayTrainings(trainingsList);
    }

    async displayTrainings(trainingsList) {
        const aboveTrainingsDisplayZoneContainer = document.getElementById("above-trainings-display-zone");
        aboveTrainingsDisplayZoneContainer.innerHTML = "Click a Training to View Details/Update";

        const trainingsDisplayZoneContainer = document.getElementById("trainings-display-zone");
        trainingsDisplayZoneContainer.className = "trainings-table";

        const tableHeaders = ["Training ID (Name/Series + Date)", "Training Status", "Active Status", "Months Til Expire", "Training Series"]

        const tbl = document.getElementById("trainings-table");
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


        const trainingSeriesChosen = this.dataStore.get("trainingSeries");
        let training;
        if (trainingsList.length === 0) {
            document.getElementById("trainings-table").innerHTML = '' + `\n` + "No trainings assigned to this Training Series: " + trainingSeriesChosen;
        } else {
            for (training of trainingsList) {
                let row = tbl.insertRow();
                row.style["color"] = "#00a5f9";
                row.style["text-decoration"] = "underline";
                row.style["cursor"] = "pointer";
                let cell1 = row.insertCell();
                let text1 = document.createTextNode(training.trainingId);
                cell1.appendChild(text1);
                let cell2 = row.insertCell();
                let text2 = document.createTextNode(training.expirationStatus);
                cell2.appendChild(text2);
                let cell3 = row.insertCell();
                let text3 = document.createTextNode(training.isActive);
                cell3.appendChild(text3);
                let cell4 = row.insertCell();
                let text4 = document.createTextNode(training.monthsTilExpire);
                cell4.appendChild(text4);
                let cell5 = row.insertCell();
                let text5 = document.createTextNode(training.trainingSeries);
                cell5.appendChild(text5);
            }
            trainingsDisplayZoneContainer.appendChild(tbl);

            var table = document.getElementById("trainings-table");
            var rows = table.getElementsByTagName("tr");
            for (var i = 0; i < rows.length; i++) {
                var currentRow = table.rows[i];
                var createClickHandler = function (row) {
                    return function () {
                        var cell1 = row.getElementsByTagName("td")[0];
                        if (cell1) {
                            var trainingId = cell1.innerHTML;
                            window.location.href = `/training.html?id=${trainingId}`;
                        }
                    };
                };
                currentRow.onclick = createClickHandler(currentRow);
            }
        }
        document.getElementById("trainingSelectDropDown").selectedIndex = 0;
        this.dataStore.set("trainingSeries", 'null');
        document.getElementById("viewAllRadioSelector").checked = false;
        this.LoadingSpinner.hideLoadingSpinner();
    }

    async redirectToTrainingView() {
        this.LoadingSpinner.showLoadingSpinner("Redirecting to Training");
        const trainingId = document.getElementById('field-zone').children[1].value;
        window.location.href = `/training.html?id=${trainingId}`;
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
