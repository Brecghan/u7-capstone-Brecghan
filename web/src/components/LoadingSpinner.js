import Matrix from "./Matrix";

export default class LoadingSpinner {

    // Function to show the loading spinner
    showLoadingSpinner(message = "") {
        document.getElementById("loading-message").innerText = "";
        document.getElementById("loading-message").style["color"] = "#39ff14";
        document.getElementById("loading-message-sub").innerText = "";
        document.getElementById("loading-message-sub").style["color"] = "#39ff14";
        document.getElementById('loading-spinner').style.display = 'flex';
        this.Matrix = new Matrix();
        this.Matrix.showMatrix();
    }

    // Function to hide the loading spinner
    hideLoadingSpinner() {
        document.getElementById('loading-spinner').style.display = 'none';

    }
}