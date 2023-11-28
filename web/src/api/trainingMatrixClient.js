import axios from "axios";
import BindingClass from "../util/bindingClass";
import Authenticator from "./authenticator";

/**
 * Client to call the TrainingMatrixService.
 *
 * This could be a great place to explore Mixins. Currently the client is being loaded multiple times on each page,
 * which we could avoid using inheritance or Mixins.
 * https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Classes#Mix-ins
 * https://javascript.info/mixins
  */
export default class TrainingMatrixClient extends BindingClass {

    constructor(props = {}) {
        super();

        const methodsToBind = ['clientLoaded', 'getIdentity', 'login', 'logout', 'getEmployee', 'getTraining', 'getTest', 'getTestList',
        'getTrainingList', 'getEmployeeList', 'getTrainingSeries', 'createEmployee', 'createTraining', 'createTest', 'createTrainingSeries',
        'updateEmployee', 'updateTraining', 'updateTest', 'deleteEmployee', 'deleteTraining', 'getTeamList'];
        this.bindClassMethods(methodsToBind, this);

        this.authenticator = new Authenticator();;
        this.props = props;

        axios.defaults.baseURL = process.env.API_BASE_URL;
        this.axiosClient = axios;
        this.clientLoaded();
    }

    /**
     * Run any functions that are supposed to be called once the client has loaded successfully.
     */
    clientLoaded() {
        if (this.props.hasOwnProperty("onReady")) {
            this.props.onReady(this);
        }
    }

    /**
     * Get the identity of the current user
     * @param errorCallback (Optional) A function to execute if the call fails.
     * @returns The user information for the current user.
     */
    async getIdentity(errorCallback) {
        try {
            const isLoggedIn = await this.authenticator.isUserLoggedIn();

            if (!isLoggedIn) {
                return undefined;
            }

            return await this.authenticator.getCurrentUserInfo();
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }

    async login() {
        this.authenticator.login();
    }

    async logout() {
        this.authenticator.logout();
    }

    async getTokenOrThrow(unauthenticatedErrorMessage) {
        const isLoggedIn = await this.authenticator.isUserLoggedIn();
        if (!isLoggedIn) {
            throw new Error(unauthenticatedErrorMessage);
        }

        return await this.authenticator.getUserToken();
    }

    /**
     * Gets the employee for the given ID.
     * @param id Unique identifier for an employee
     * @param errorCallback (Optional) A function to execute if the call fails.
     * @returns The employee's metadata.
     */
    async getEmployee(id, errorCallback) {
        try {
            const response = await this.axiosClient.get(`employee/${id}`);
            return response.data.employee;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }

    /**
     * Gets the training for the given ID.
     * @param id Unique identifier for a training
     * @param errorCallback (Optional) A function to execute if the call fails.
     * @returns The training's metadata.
     */
    async getTraining(id, errorCallback) {
        try {
            const response = await this.axiosClient.get(`training/${id}`);
            return response.data.training;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }

    /**
     * Gets the test for the given ID.
     * @param id Unique identifier for a test
     * @param errorCallback (Optional) A function to execute if the call fails.
     * @returns The test's metadata.
     */
    async getTest(id, errorCallback) {
        try {
            const response = await this.axiosClient.get(`test/${id}`);
            return response.data.test;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }

     /**
     * Gets the test list with the passed criteria.
     * @param criteria A string containing search criteria to pass to the API.
     * @param errorCallback (Optional) A function to execute if the call fails.
     * @returns The tests that match the search criteria.
     */
    async getTestList(criteria, errorCallback) {
        try {
            const queryParams = new URLSearchParams({ q: criteria })
            const queryString = queryParams.toString();

            const response = await this.axiosClient.get(`test?${queryString}`);
            return response.data.tests;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }

     /**
     * Gets the training list with the passed criteria.
     * @param criteria A string containing search criteria to pass to the API.
     * @param errorCallback (Optional) A function to execute if the call fails.
     * @returns The trainings that match the search criteria.
     */
     async getTrainingList(criteria, errorCallback) {
        try {
            const queryParams = new URLSearchParams({ q: criteria })
            const queryString = queryParams.toString();

            const response = await this.axiosClient.get(`training?${queryString}`);
            return response.data.trainings;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }    

     /**
     * Gets the employee list with the passed criteria.
     * @param criteria A string containing search criteria to pass to the API.
     * @param errorCallback (Optional) A function to execute if the call fails.
     * @returns The employees that match the search criteria.
     */
     async getEmployeeList(criteria, errorCallback) {
        try {
            const queryParams = new URLSearchParams({ q: criteria })
            const queryString = queryParams.toString();

            const response = await this.axiosClient.get(`employee?${queryString}`);
            return response.data.employees;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }        

    /**
     * Gets the training series
     * @param errorCallback (Optional) A function to execute if the call fails.
     * @returns The training series' metadata.
     */
    async getTrainingSeries(errorCallback) {
        try {
            const response = await this.axiosClient.get(`trainingSeries`);
            return response.data.trainingSeries;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }

    /**
     * Create a new employee.
     * @param employeeName The name of the employee to create.
     * @param employeeId The ID of the employee to create.
     * @param team The team of the employee to create.
     * @param startDate The startDate of the employee to create.
     * @param errorCallback (Optional) A function to execute if the call fails.
     * @returns The employee that has been created.
     */
    async createEmployee(employeeName, employeeId, team, startDate, errorCallback) {
        try {
            const token = await this.getTokenOrThrow("Only authenticated users can create employees.");
            const response = await this.axiosClient.post(`employee`, {
                employeeName: employeeName,
                employeeId: employeeId,
                team: team,
                startDate: startDate
            }, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            return response.data.emnployee;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }

    /**
     * Create a new training.
     * @param trainingName The name of the training to create.
     * @param trainingSeries The series of the training to create.
     * @param monthsTilExpire The months until the training expires.
     * @param trainingDate The startDate of the employee to create.
     * @param errorCallback (Optional) A function to execute if the call fails.
     * @returns The training that has been created.
     */
    async createTraining(trainingName, trainingSeries, monthsTilExpire, trainingDate, errorCallback) {
        try {
            const token = await this.getTokenOrThrow("Only authenticated users can create trainings.");
            const response = await this.axiosClient.post(`training`, {
                trainingName: trainingName,
                trainingSeries: trainingSeries,
                monthsTilExpire: monthsTilExpire,
                trainingDate: trainingDate
            }, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            return response.data.training;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }

    /**
     * Create new tests for all identified employees.
     * @param trainingId The Id of the training for the test to create.
     * @param employeeIds The Ids of the Employees for the tests created.
     * @param scoreToPass The scoree needed to pass the test.
     * @param errorCallback (Optional) A function to execute if the call fails.
     * @returns The tests that have been created.
     */
    async createTest(trainingId, employeeIds, scoreToPass, errorCallback) {
        try {
            const token = await this.getTokenOrThrow("Only authenticated users can create tests.");
            const response = await this.axiosClient.post(`test`, {
                trainingId: trainingId,
                employeeIds: employeeIds,
                scoreToPass: scoreToPass
            }, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            return response.data.tests;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }

    /**
     * Create new training series.
     * @param trainingSeriesName The name of the training series to create.
     * @param errorCallback (Optional) A function to execute if the call fails.
     * @returns The training series that has been created.
     */
    async createTrainingSeries(trainingSeriesName, errorCallback) {
        try {
            const token = await this.getTokenOrThrow("Only authenticated users can create tests.");
            const response = await this.axiosClient.post(`trainingSeries`, {
                trainingSeriesName: trainingSeriesName,
            }, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            return response.data.traingSeries;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }

    /**
     * Update an employee.
     * @param employeeName The name of the employee to update.
     * @param employeeId The ID of the employee to update.
     * @param team The team of the employee to update.
     * @param isActive The active status of the employee to update.
     * @param trainingsTaken The trainings of the employee to update.
     * @param testsTaken The tests of the employee to update.
     * @param errorCallback (Optional) A function to execute if the call fails.
     * @returns The employee that has been updated.
     */
    async updateEmployee(employeeName, employeeId, team, isActive, trainingsTaken, testsTaken, errorCallback) {
        try {
            const token = await this.getTokenOrThrow("Only authenticated users can update employees.");
            const response = await this.axiosClient.put(`employee/${id}`, {
                employeeName: employeeName,
                employeeId: employeeId,
                team: team,
                isActive: isActive,
                trainingsTaken: trainingsTaken,
                testsTaken: testsTaken
            }, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            return response.data.employee;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }

    /**
     * Update a training.
     * @param trainingId The name of the training to update.
     * @param isActive The sctive status of the training to update.
     * @param monthsTilExpire The months until the training expires.
     * @param employeesTrained The employees of the training to update.
     * @param testsForTraining The tests of the training to update.
     * @param expirationStatus The expiration status of the employee to update.
     * @param errorCallback (Optional) A function to execute if the call fails.
     * @returns The training that has been updated.
     */
    async updateTraining(trainingId, isActive, monthsTilExpire, employeesTrained, testsForTraining, expirationStatus, errorCallback) {
        try {
            const token = await this.getTokenOrThrow("Only authenticated users can update trainings.");
            const response = await this.axiosClient.put(`training/${id}`, {
                trainingId: trainingId,
                isActive: isActive,
                monthsTilExpire: monthsTilExpire,
                employeesTrained: employeesTrained,
                testsForTraining: testsForTraining,
                expirationStatus: expirationStatus
            }, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            return response.data.training;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }

    /**
     * Update a test
     * @param trainingId The Id of the training for the test to update.
     * @param employeeId The Id of the Employees for the test to update.
     * @param latestScore The score of the test attempt
     * @param errorCallback (Optional) A function to execute if the call fails.
     * @returns The tests that have been created.
     */
    async updateTest(trainingId, employeeId, latestScore, errorCallback) {
        try {
            const token = await this.getTokenOrThrow("Only authenticated users can create tests.");
            const response = await this.axiosClient.put(`test/${id}`, {
                trainingId: trainingId,
                employeeId: employeeId,
                latestScore: latestScore
            }, {
                headers: {
                    Authorization: `Bearer ${token}`
                }
            });
            return response.data.tests;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }

    /**
     * Deletes the employee for the given ID.
     * @param id Unique identifier for an employee
     * @param errorCallback (Optional) A function to execute if the call fails.
     * @returns The employee's metadata.
     */
    async deleteEmployee(id, errorCallback) {
        try {
            const response = await this.axiosClient.delete(`employee/${id}`);
            return response.data.employee;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }

    /**
     * Delete the training for the given ID.
     * @param id Unique identifier for a training
     * @param errorCallback (Optional) A function to execute if the call fails.
     * @returns The training's metadata.
     */
    async deleteTraining(id, errorCallback) {
        try {
            const response = await this.axiosClient.delete(`training/${id}`);
            return response.data.training;
        } catch (error) {
            this.handleError(error, errorCallback)
        }
    }

       /**
     * Gets the Team List
     * @returns The team list.
     */
       async getTeamList() {
        const teamList = ["Saab", "Volvo", "BMW"];
        return teamList;
    } 

    /**
     * Helper method to log the error and run any error functions.
     * @param error The error received from the server.
     * @param errorCallback (Optional) A function to execute if the call fails.
     */
    handleError(error, errorCallback) {
        console.error(error);

        const errorFromApi = error?.response?.data?.error_message;
        if (errorFromApi) {
            console.error(errorFromApi)
            error.message = errorFromApi;
        }

        if (errorCallback) {
            errorCallback(error);
        }
    }
}
