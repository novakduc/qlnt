package com.novakduc.forbega.qlnt.data.database;

import com.novakduc.forbega.qlnt.ui.detail.ProjectNameQuery;
import com.novakduc.forbega.qlnt.ui.detail.finance.BillRecentItem;
import com.novakduc.forbega.qlnt.ui.detail.finance.CostRecentItem;
import com.novakduc.forbega.qlnt.ui.detail.finance.ProjectFinanceTab;
import com.novakduc.forbega.qlnt.ui.detail.room.GuestForRoomItemView;
import com.novakduc.forbega.qlnt.ui.detail.room.ListViewRoomItem;
import com.novakduc.forbega.qlnt.ui.list.ListViewProjectItem;
import com.novakduc.forbega.qlnt.ui.list.LoanAmount;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

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

    //Get a loan
    @Query("SELECT * FROM loan WHERE id = :id")
    Loan getLoanById(long id);

    //Get live data loan by id
    @Query("SELECT * FROM loan WHERE id = :loanId")
    LiveData<Loan> getLiveDataLoanById(long loanId);

    //Get a project
    @Query("SELECT * FROM project WHERE projectId = :projectId")
    LiveData<Project> getLiveDataProject(long projectId);

    //Get a project
    @Query("SELECT * FROM project WHERE projectId = :projectId")
    Project getProject(long projectId);

    //Get all project
    @Query("SELECT projectId, name, investmentAmount, startDate, yearDuration FROM project")
    LiveData<List<ListViewProjectItem>> getAllProjects();

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
    void removeRoomForRent(RoomForRent roomForRent);

    @Delete
    void removeRoomList(RoomList roomList);

    //Get project unit price
    @Query("SELECT * FROM unit_price WHERE id = :projectId")
    UnitPrice getUnitPrice(long projectId);

    //Get a project loan_list
    @Query("SELECT * FROM loan WHERE projectId = :projectId")
    List<Loan> getLoanList(long projectId);

    //Get a project room list
    @Query("SELECT id, name, status FROM room WHERE projectId = :projectId")
    LiveData<List<ListViewRoomItem>> getListViewRoomItems(long projectId);

    //Get a project room list
    @Query("SELECT * FROM room_list WHERE projectId = :projectId")
    RoomList getRoomList(long projectId);

    //Get room by id
    @Query("SELECT * FROM room WHERE id = :roomId")
    RoomForRent getRoomById(long roomId);

    //Get a project cost manager
    @Query("SELECT * FROM cost_manager WHERE projectId = :projectId")
    CostManager getCostManager(long projectId);

    //Get a project room list
    @Query("SELECT * FROM cost WHERE id = :costId")
    Cost getCostById(long costId);

    //Delete cost
    @Delete
    void removeCost(Cost cost);

    //Delete cost manager
    @Delete
    void removeCostManger(CostManager costManager);

    //Update project
    @Update
    void updateProject(Project project);

    //Insert a loan to database
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Loan loan);

    //Update loan
    @Update
    void updateLoan(Loan loan);

    //Get all loan in project, return live data
    @Query("SELECT * FROM loan WHERE projectId = :projectId")
    LiveData<List<Loan>> getAllLoanInProject(long projectId);

    //Get all invalid loan
    @Query("SELECT * FROM loan WHERE amount = 0")
    List<Loan> getInvalidLoans();

    //Get project unit price, return live data
    @Query("SELECT * FROM unit_price WHERE id = :projectId")
    LiveData<UnitPrice> getUnitPriceLiveData(long projectId);

    //Update unit price
    @Update
    void updateUnitPrice(UnitPrice unitPrice);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(UnitPrice unitPrice);

    @Query("SELECT amount, projectId FROM loan")
    LiveData<List<LoanAmount>> getAllLoanAmount();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(RoomForRent tmpRoomForRent);

    @Query("SELECT name, phoneNumber, roomId FROM guest WHERE keyContact > 0")
    LiveData<List<GuestForRoomItemView>> getAllKeyContact();

    @Query("SELECT * FROM guest WHERE roomId = :roomId")
    List<Guest> getGuestsByRoom(long roomId);

    @Delete
    void removeGuest(Guest guest);

    @Update
    void updateRoomForRent(RoomForRent roomForRent);

    @Query("SELECT * FROM room WHERE id = :roomId")
    LiveData<RoomForRent> getLiveDataRoomById(long roomId);

    @Query("SELECT revenue, dept, cost FROM project WHERE projectId = :mProjectId")
    LiveData<ProjectFinanceTab> getProjectFinanceInfo(long mProjectId);

    @Query("SELECT amount, paymentDate, description  FROM bill WHERE paymentDate > :timeFrame AND isPaid > 0")
    List<BillRecentItem> getRecentBills(long timeFrame);

    @Query("SELECT amount, date, type, description FROM cost WHERE date > :timeFrame")
    List<CostRecentItem> getRecentCosts(long timeFrame);

    @Query("SELECT name FROM project WHERE projectId = :pProjectId")
    LiveData<ProjectNameQuery> getProjectName(long pProjectId);

    //Insert a guest to database
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Guest guest);

    @Query("SELECT * FROM guest WHERE id = :pGuestId")
    LiveData<Guest> getLiveDataGuestById(long pGuestId);

    @Update
    void updateGuest(Guest pGuest);

    @Query("SELECT * FROM guest WHERE roomId = :pRoomId")
    LiveData<List<Guest>> getLiveDataGuestListByRoomId(long pRoomId);

    @Query("SELECT * FROM guest WHERE roomId =  :pRoomId")
    List<Guest> getGuestsByRoomId(long pRoomId);

    @Query("SELECT * FROM guest WHERE id = :pGuestId")
    Guest getGuestById(long pGuestId);

    //Insert a room service to database
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(RoomService roomService);

    @Query("SELECT * FROM room_service WHERE id = :pRoomId")
    List<RoomService> getAllServicesByRoomId(long pRoomId);

    @Delete
    void removeRoomService(RoomService pRoomService);

    @Query("SELECT * FROM room_service WHERE roomId = :pRoomId")
    LiveData<List<RoomService>> getLiveDataServices(long pRoomId);

    @Update
    void updateService(RoomService pService);
}
