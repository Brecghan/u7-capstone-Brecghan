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
        this.bindClassMethods(['mount', 'submit', 'redirectToViewTraining', 'addTrainingSelect', 'newTrainingSeries', 'submitTrainingSeries' ], this);
        this.dataStore = new DataStore();
        this.dataStore.addChangeListener(this.redirectToViewTraining);
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
            if (!document.getElementById("series-name").value) {
                alert('Please enter a Training Series name');
            } else {
            this.submitTrainingSeries();}
        });

        this.header.addHeaderToPage();

        this.client = new TrainingMatrixClient();
        this.LoadingSpinner.showLoadingSpinner("Loading Page");
        this.addTrainingSelect();
    }

    /**
     * Method to run when the create training submit button is pressed. Call the TrainingMatrixClient to create the
     * training.
     */
    async submit(evt) {
        evt.preventDefault();
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
        this.dataStore.set('training', training);
    }
    }

    /**
     * When the training is updated in the datastore, redirect to the view training page.
     */
    async redirectToViewTraining() {
        this.LoadingSpinner.showLoadingSpinner("Redirecting to Training");
        const training = this.dataStore.get('training');
        if (training != null) {
            window.location.href = `/training.html?id=${training.trainingId}`;
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

    newTrainingSeries(){
        var modal = document.getElementById("myModal");
        modal.style.display = "block";
    }

    async submitTrainingSeries() {
        const newTrainingSeries = document.getElementById("series-name").value;
        this.LoadingSpinner.showLoadingSpinner("Creating Training Series");
        console.log(newTrainingSeries);
        const results = await this.client.createTrainingSeries(newTrainingSeries);
        if (results != null){
            window.location.href = `/createTraining.html`;
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
