package com.novakduc.forbega.qlnt.data.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by Nguyen Quoc Thanh on 1/3/2018.
 */

@Dao
public interface QlntDao {

    //Get all costs in project
    @Query("SELECT * FROM cost WHERE projectId = :projectId")
    List<Cost> getAllCost(long projectId);

    //Get all costs in project with specified type
    @Query("SELECT * FROM cost WHERE projectId = :projectId AND type = :type")
    List<Cost> getAllCostByType(long projectId, CostType type);

    //Insert a cost to cost table
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Cost cost);

    //Get all loan in project
    @Query("SELECT * FROM loan WHERE projectId = :projectId")
    List<Loan> getAllLoan(long projectId);

    //Get a loan
    @Query("SELECT * FROM loan WHERE projectId = :projectId AND id = :id")
    Loan getLoanById(long projectId, long id);

    //Get a project
    @Query("SELECT * FROM project WHERE projectId = :projectId")
    Project getProject(long projectId);

    //Get all project
    @Query("SELECT * FROM project")
    List<Cost> getAllProjects();

    //Insert a project to database
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Project project);
}
