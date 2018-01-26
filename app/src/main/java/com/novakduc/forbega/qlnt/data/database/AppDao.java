package com.novakduc.forbega.qlnt.data.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by Nguyen Quoc Thanh on 1/3/2018.
 */

@Dao
public interface AppDao {

    //Get all costs in project
    @Query("SELECT * FROM cost")
    List<Cost> getAllCost();

    //Get all costs in project with specified type
    @Query("SELECT * FROM cost WHERE type = :type")
    List<Cost> getAllCostByType(CostType type);

    //Get all costs in project with specified date
    @Query("SELECT * FROM cost WHERE date = :date")
    List<Cost> getAllCostByDate(long date);

    //Insert a cost to cost table
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Cost cost);

    //Get all loan in project
    @Query("SELECT * FROM loan")
    List<Loan> getAllLoan();

    //Get a loan
    @Query("SELECT * FROM loan WHERE id = :id")
    Loan getLoanById(long id);

    //Get a project
    @Query("SELECT * FROM project WHERE projectId = :projectId")
    LiveData<Project> getLiveDataProject(long projectId);

    //Get a project
    @Query("SELECT * FROM project WHERE projectId = :projectId")
    Project getProject(long projectId);

    //Get all project
    @Query("SELECT * FROM project")
    LiveData<List<Project>> getAllProjects();

    //Insert a project to database
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Project project);

    @Delete
    void removeProject(Project project);

    @Delete
    void removeUnitPrice(UnitPrice unitPrice);

    @Delete
    void removeLoan(Loan l);

    @Delete
    void removeLoanList(LoanList loans);

    @Delete
    void removeRoomForRent(RoomForRent roomForRent);

    @Delete
    void removeRoomList(RoomList roomList);

    //Get project unit price
    @Query("SELECT * FROM unit_price WHERE id = :projectId")
    UnitPrice getUnitPrice(long projectId);

    //Get a project loan list
    @Query("SELECT * FROM loan_list WHERE projectId = :projectId")
    LoanList getLoanList(long projectId);

    //Get a project room list
    @Query("SELECT * FROM room_list WHERE projectId = :projectId")
    RoomList getRoomList(long projectId);

    //Get a project room list
    @Query("SELECT * FROM room WHERE id = :roomId")
    RoomForRent getRoomById(long roomId);

    //Get a project cost manager
    @Query("SELECT * FROM cost_manager WHERE projectId = :projectId")
    CostManager getCostManager(long projectId);

    //Get a project room list
    @Query("SELECT * FROM cost WHERE id = :costId")
    Cost getCostById(long costId);

    @Delete
    void removeCost(Cost cost);

    @Delete
    void removeCostManger(CostManager costManager);

    @Update
    void updateProject(Project project);
}
