package com.nashss.se.trainingmatrix.dependency;

import com.nashss.se.trainingmatrix.activity.GetEmployeeActivity;

import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = {DaoModule.class})
public interface ServiceComponent {
//
    //    /**
    //     * Provides the relevant activity.
    //     * @return CreateEmployeeActivity
    //     */
    //   CreateEmployeeActivity provideCreateEmployeeActivity();

    /**
     * Provides the relevant activity.
     * @return GetEmployeeActivity
     */
    GetEmployeeActivity provideGetEmployeeActivity();

    /**
     * Provides the relevant activity.
     * @return GetEmployeeListActivity
     */
//    GetEmployeeListActivity provideGetEmployeeListActivity();
//
//    /**
//     * Provides the relevant activity.
//     * @return UpdateEmployeeActivity
//     */
//    UpdateEmployeeActivity provideUpdateEmployeeActivity();
//
//    /**
//     * Provides the relevant activity.
//     * @return CreateTrainingActivity
//     */
//    CreateTrainingActivity provideCreateTrainingActivity();
//
//    /**
//     * Provides the relevant activity.
//     * @return GetTrainingActivity
//     */
//    GetTrainingActivity provideGetTrainingActivity();
//
//    /**
//     * Provides the relevant activity.
//     * @return GetTrainingListActivity
//     */
//    GetTrainingListActivity provideGetTrainingListActivity();
//
//    /**
//     * Provides the relevant activity.
//     * @return UpdateTrainingActivity
//     */
//    UpdateTrainingActivity provideUpdateTrainingActivity();
//
//    /**
//     * Provides the relevant activity.
//     * @return CreateTestActivity
//     */
//    CreateTestActivity provideCreateTestActivity();
//
//    /**
//     * Provides the relevant activity.
//     * @return GetTestActivity
//     */
//    GetTestActivity provideGetTestActivity();
//
//    /**
//     * Provides the relevant activity.
//     * @return GetTestListActivity
//     */
//    GetTestListActivity provideGetTestListActivity();
//
//    /**
//     * Provides the relevant activity.
//     * @return UpdateTestActivity
//     */
//    UpdateTestActivity provideUpdateTestActivity();
//
//    /**
//     * Provides the relevant activity.
//     * @return CreateTrainingSeriesActivity
//     */
//    CreateTrainingSeriesActivity provideCreateTrainingSeriesActivity();
//
    //    /**
    //     * Provides the relevant activity.
    //     * @return GetTrainingSeriesActivity
    //     */
    //    GetTrainingSeriesActivity provideGetTrainingSeriesActivity();
}
