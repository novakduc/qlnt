/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.novakduc.forbega.qlnt.utilities;

import android.content.Context;
import android.support.annotation.NonNull;

import com.novakduc.baselibrary.AppExecutors;
import com.novakduc.forbega.qlnt.data.ProjectRepo;
import com.novakduc.forbega.qlnt.data.QlntRepository;
import com.novakduc.forbega.qlnt.data.RoomForRentRepo;
import com.novakduc.forbega.qlnt.data.database.AppDatabase;
import com.novakduc.forbega.qlnt.ui.config.ProjectConfigViewModelFactory;
import com.novakduc.forbega.qlnt.ui.config.base.ProjectBaseViewModelFactory;
import com.novakduc.forbega.qlnt.ui.config.finance.ProjectFinanceConfigViewModelFactory;
import com.novakduc.forbega.qlnt.ui.config.finance.loan.LoanDeclareViewModelFactory;
import com.novakduc.forbega.qlnt.ui.config.unitprice.UnitPriceConfigViewModelFactory;
import com.novakduc.forbega.qlnt.ui.detail.ProjectDetailViewModelFactory;
import com.novakduc.forbega.qlnt.ui.detail.finance.FinanceViewModelFactory;
import com.novakduc.forbega.qlnt.ui.detail.report.ReportViewModelFactory;
import com.novakduc.forbega.qlnt.ui.detail.room.RoomListViewModelFactory;
import com.novakduc.forbega.qlnt.ui.detail.room.add_room.AddRoomViewModelFactory;
import com.novakduc.forbega.qlnt.ui.detail.room.checkin.CheckInViewModelFactory;
import com.novakduc.forbega.qlnt.ui.detail.room.checkin.add_guest.AddGuestViewModelFactory;
import com.novakduc.forbega.qlnt.ui.detail.room.edit_room.EditRoomViewModelFactory;
import com.novakduc.forbega.qlnt.ui.list.ProjectListViewModelFactory;

/**
 * Provides static methods to inject the various classes needed for QLNT
 */
public final class InjectorUtils {

    private InjectorUtils() { }

    public static QlntRepository provideRepository(Context context) {
        AppDatabase database = AppDatabase.getInstance(context);
        AppExecutors executors = AppExecutors.getInstance();
//        WeatherNetworkDataSource networkDataSource =
//                WeatherNetworkDataSource.getInstance(context.getApplicationContext(), executors);
        return QlntRepository.getInstance(database.appDao(), executors);
    }

    public static ProjectRepo provideProjectRepo(Context context, long projectId) {
        AppDatabase database = AppDatabase.getInstance(context);
        AppExecutors executors = AppExecutors.getInstance();
        return ProjectRepo.getInstance(database.appDao(), executors, projectId);
    }

    private static RoomForRentRepo provideRoomForRentRepo(Context context, long roomId) {
        AppDatabase database = AppDatabase.getInstance(context);
        AppExecutors executors = AppExecutors.getInstance();
        return RoomForRentRepo.getInstance(database.appDao(), executors, roomId);
    }
//    public static WeatherNetworkDataSource provideNetworkDataSource(Context context) {
//        provideRepository(context);
//        AppExecutors executors = AppExecutors.getInstance();
//        return WeatherNetworkDataSource.getInstance(context.getApplicationContext(), executors);
//    }
//
//    public static DetailViewModelFactory provideDetailViewModelFactory(Context context, Date date) {
//        SunshineRepository repository = provideRepository(context.getApplicationContext());
//        return new DetailViewModelFactory(repository, date);
//    }

    @NonNull
    public static ProjectListViewModelFactory provideProjectListViewModelFactory(Context context) {
        QlntRepository repository = InjectorUtils.provideRepository(context.getApplicationContext());
        return new ProjectListViewModelFactory(repository);
    }

    @NonNull
    public static ProjectBaseViewModelFactory provideProjectBaseViewModelFactory(Context context) {
        QlntRepository repository = InjectorUtils.provideRepository(context);
        return new ProjectBaseViewModelFactory(repository);
    }

    @NonNull
    public static ProjectFinanceConfigViewModelFactory provideProjectFinanceConfigViewModelFactory(
            Context context, long projectId) {
        ProjectRepo projectRepo = provideProjectRepo(context, projectId);
        return new ProjectFinanceConfigViewModelFactory(projectRepo);
    }

    @NonNull
    public static LoanDeclareViewModelFactory provideLoanDeclareViewModelFactory(
            Context context, long projectId, boolean isNew) {
        ProjectRepo projectRepo = provideProjectRepo(context, projectId);
        return new LoanDeclareViewModelFactory(projectRepo, isNew);
    }

    @NonNull
    public static ProjectConfigViewModelFactory provideProjectConfigViewModelFactory(Context context) {
        QlntRepository repository = provideRepository(context);
        return new ProjectConfigViewModelFactory(repository);
    }

    @NonNull
    public static UnitPriceConfigViewModelFactory provideUnitPriceConfigViewModelFactory(
            Context context, long projectId) {
        ProjectRepo projectRepo = provideProjectRepo(context, projectId);
        return new UnitPriceConfigViewModelFactory(projectRepo);
    }

    @NonNull
    public static RoomListViewModelFactory provideRoomListViewModelFactory(Context context, long projectId) {
        ProjectRepo projectRepo = provideProjectRepo(context, projectId);
        return new RoomListViewModelFactory(projectRepo);
    }

    @NonNull
    public static AddRoomViewModelFactory provideAddRoomViewModelFactory(Context context, long projectId) {
        ProjectRepo projectRepo = provideProjectRepo(context, projectId);
        return new AddRoomViewModelFactory(projectRepo);
    }

    public static EditRoomViewModelFactory provideEditRoomViewModelFactory(Context context, long roomId) {
        RoomForRentRepo roomForRentRepo = provideRoomForRentRepo(context, roomId);
        return new EditRoomViewModelFactory(roomForRentRepo);
    }

    public static FinanceViewModelFactory provideFinanceViewModelFactory(Context context, long projectId) {
        ProjectRepo projectRepo = provideProjectRepo(context, projectId);
        return new FinanceViewModelFactory(projectRepo);
    }

    public static ProjectDetailViewModelFactory provideProjectDetailViewModelFactory(Context pContext, long pProjectId) {
        ProjectRepo projectRepo = provideProjectRepo(pContext, pProjectId);
        return new ProjectDetailViewModelFactory(projectRepo);
    }

    public static ReportViewModelFactory provideReportViewModelFactory(Context pContext, long pProjectId) {
        ProjectRepo projectRepo = provideProjectRepo(pContext, pProjectId);
        return new ReportViewModelFactory(projectRepo);
    }

    public static CheckInViewModelFactory provideCheckInViewModelFactory(Context pContext, long pRoomId) {
        RoomForRentRepo roomForRentRepo = provideRoomForRentRepo(pContext, pRoomId);
        return new CheckInViewModelFactory(roomForRentRepo);
    }

    public static AddGuestViewModelFactory provideAddGuestViewModelFactory(Context pContext, long pRoomId, Boolean pIsNew) {
        RoomForRentRepo roomForRentRepo = provideRoomForRentRepo(pContext, pRoomId);
        return new AddGuestViewModelFactory(roomForRentRepo, pIsNew);
    }
}