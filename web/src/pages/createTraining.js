import TrainingMatrixClient from '../api/trainingMatrixClient';
import Header from '../components/header';
import BindingClass from '../util/bindingClass';
import DataStore from '../util/DataStore';
import LoadingSpinner from '../components/LoadingSpinner';

/**
 * Logic needed for the create employee page of the website.
 */
class CreateTraining extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['mount', 'submit', 'addTrainingSelect', 'newTrainingSeries', 'submitTrainingSeries'], this);
        this.dataStore = new DataStore();
        this.header = new Header(this.dataStore);
        this.LoadingSpinner = new LoadingSpinner;
    }

    /**
     * Add the header to the page and load the TrainingMatrixClient.
     */
    mount() {
        document.getElementById('create').addEventListener('click', this.submit);
        document.getElementById('createTrainingSeries').addEventListener('click', this.newTrainingSeries);
        document.getElementById('submit-update-btn').addEventListener('click', () => {
            if (!document.getElementById("series-name-value").value) {
                alert('Please enter a Training Series name');
            } else {
                this.submitTrainingSeries(document.getElementById("series-name-value").value);
            }
        });

        this.header.addHeaderToPage();

        this.client = new TrainingMatrixClient();
        this.LoadingSpinner.showLoadingSpinner("Loading Page");
        this.addTrainingSelect();
        window.onclick = function (event) {
            if (event.target === document.getElementById("myModal")) {
                document.getElementById("myModal").style.display = "none";
                document.getElementById("series-name").removeChild(document.getElementById("series-name").lastChild);
            }
        }
    }

    /**
     * Method to run when the create training submit button is pressed. Call the TrainingMatrixClient to create the
     * training.
     */
    async submit() {
        this.LoadingSpinner.showLoadingSpinner('Creating Training');
        const errorMessageDisplay = document.getElementById('error-message');
        errorMessageDisplay.innerText = ``;
        errorMessageDisplay.classList.add('hidden');


        const trainingName = document.getElementById('training-name').value;
        const monthsTilExpire = document.getElementById('months-til').value;
        var trainingDate = document.getElementById('trainingDate').value;
        trainingDate += 'Z[UTC]';
        const trainingSeries = document.getElementById('training-series-field').children[1].value;
        if (!trainingName || !monthsTilExpire || !document.getElementById('trainingDate').value) {
            alert('Please fill in all information')
            this.LoadingSpinner.hideLoadingSpinner();
        } else {
            const training = await this.client.createTraining(trainingName, trainingSeries, monthsTilExpire, trainingDate, (error) => {
                errorMessageDisplay.innerText = `Error: ${error.message}`;
                errorMessageDisplay.classList.remove('hidden');
            });
            this.LoadingSpinner.showLoadingSpinner("Redirecting to Training");
            if (training) {
                window.location.href = `/training.html?id=${training.trainingId}`;
            }
        }
    }

    async addTrainingSelect() {
        const trainingSeriesList = await this.client.getTrainingSeries();

        const searchByTraining = document.getElementById("training-series-field");

        let selectTag = document.createElement('select');
        let opt = document.createElement("option");
        opt.value = null; // the index
        opt.innerHTML = 'Select Series if Applicable';
        selectTag.append(opt);
        trainingSeriesList.forEach(tsl => {
            let opt = document.createElement("option");
            opt.value = tsl.trainingSeriesName; // the index
            opt.innerHTML = tsl.trainingSeriesName;
            selectTag.append(opt);
        });
        searchByTraining.appendChild(selectTag);
        this.LoadingSpinner.hideLoadingSpinner();
    }

    newTrainingSeries() {
        var modal = document.getElementById("myModal");
        modal.style.display = "block";
        const newTrainingSeries = document.getElementById("series-name");
        let seriesText = document.createElement('input');
        seriesText.setAttribute("type", "text");
        seriesText.setAttribute("placeholder", "Enter new series name");
        seriesText.setAttribute("id", "series-name-value");
        newTrainingSeries.appendChild(seriesText);
    }

    async submitTrainingSeries(newTrainingSeries) {
        this.LoadingSpinner.showLoadingSpinner("Creating Training Series");
        const results = await this.client.createTrainingSeries(newTrainingSeries);
        if (results) {
            const docClear = document.getElementById("training-series-field");
            const docClear2 = document.getElementById("series-name");
            docClear.removeChild(docClear.lastChild);
            docClear2.removeChild(docClear2.lastChild);
            this.addTrainingSelect();
            var modal = document.getElementById("myModal");
            modal.style.display = "none";
        }
    }
}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const createTraining = new CreateTraining();
    createTraining.mount();
};

window.addEventListener('DOMContentLoaded', main);
