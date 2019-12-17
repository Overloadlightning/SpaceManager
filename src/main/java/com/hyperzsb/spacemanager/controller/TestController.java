package com.hyperzsb.spacemanager.controller;

import com.hyperzsb.spacemanager.domain.Academy;
import com.hyperzsb.spacemanager.domain.Borrower;
import com.hyperzsb.spacemanager.domain.BorrowingOrder;
import com.hyperzsb.spacemanager.domain.Room;
import com.hyperzsb.spacemanager.repository.AcademyRepository;
import com.hyperzsb.spacemanager.repository.BorrowerRepository;
import com.hyperzsb.spacemanager.repository.BorrowingOrderRepository;
import com.hyperzsb.spacemanager.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@Controller
@RequestMapping("/test")
public class TestController {
    @Autowired
    private AcademyRepository academyRepository;
    @Autowired
    private BorrowerRepository borrowerRepository;
    @Autowired
    private BorrowingOrderRepository borrowingOrderRepository;
    @Autowired
    private RoomRepository roomRepository;

    @RequestMapping("/insertAcademy")
    public void insertAcademy(Integer id, String name) {
        Academy academy = new Academy(id, name);
        academyRepository.save(academy);
    }

    @RequestMapping("/getAcademy")
    @ResponseBody
    public Academy getAcademy(Integer id) {
        Academy academy = (Academy) academyRepository.findById(id).get();
        return academy;
    }

    @RequestMapping("/getAcademies")
    @ResponseBody
    public List<Academy> getAcademies(Integer id) {
        List<Academy> academyList = academyRepository.findAll();
        return academyList;
    }

    @RequestMapping("/updateAcademy")
    @ResponseBody
    public Academy updateAcademy(Integer id, String name) {
        Academy academy = new Academy(id, name);
        academyRepository.save(academy);
        return academy;
    }

    @RequestMapping("/deleteAcademy")
    public void deleteAcademy(Integer id) {
        Academy academy = academyRepository.findById(id).get();
        academyRepository.delete(academy);
    }

    @RequestMapping("/insertBorrower")
    public void insertBorrower(Integer id, String name, Integer academyId) {
        Academy academy = academyRepository.findById(academyId).get();
        Borrower borrower = new Borrower(id, name, academy);
        borrowerRepository.save(borrower);
    }

    @RequestMapping("/getBorrower")
    @ResponseBody
    public Borrower getBorrower(Integer id) {
        Borrower borrower = borrowerRepository.findById(id).get();
        return borrower;
    }

    @RequestMapping("/getBorrowers")
    @ResponseBody
    public List<Borrower> getBorrowers() {
        List<Borrower> borrowerList = borrowerRepository.findAll();
        return borrowerList;
    }

    @RequestMapping("/insertRoom")
    public void insertRoom(Integer id, String name, String note, int availabilityValue) {
        Room room = new Room(id, name, note, availabilityValue);
        roomRepository.save(room);
    }

    @RequestMapping("/getRooms")
    @ResponseBody
    public List<Room> getRooms() {
        List<Room> roomList = roomRepository.findAll();
        for (Room room : roomList) {
            System.out.println();
        }
        return roomList;
    }
    //insertOrder?borrowerId=1120180000&roomId=2&note=借用研讨室开会&timeStr=2019-12-17%2021:25&startTimeStr=2019-12-18%2008:30&endTimeStr=2019-12-18%2011:00
    @RequestMapping("/insertOrder")
    public void insertOrder(Integer borrowerId, Integer roomId, String note, String timeStr, String startTimeStr, String endTimeStr) throws ParseException {
        Timestamp time = new Timestamp(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(timeStr).getTime());
        Timestamp startTime = new Timestamp(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(startTimeStr).getTime());
        Timestamp endTime = new Timestamp(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(endTimeStr).getTime());
        Borrower borrower = borrowerRepository.findById(borrowerId).get();
        Room room = roomRepository.findById(roomId).get();
        BorrowingOrder borrowingOrder = new BorrowingOrder(borrower, room, note, time, startTime, endTime);
        borrowingOrderRepository.save(borrowingOrder);
    }

    @RequestMapping("/getOrders")
    @ResponseBody
    public List<BorrowingOrder> getOrders() {
        List<BorrowingOrder> borrowingOrderList = borrowingOrderRepository.findAll();
        return borrowingOrderList;
    }
}
