/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timers;

import java.util.Date;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;

/**
 *
 * @author okaracalik
 */
@Startup
@Singleton
public class TaskManager {

    @Schedule(dayOfWeek = "Mon-Fri", month = "*", hour = "9-17", dayOfMonth = "*", year = "*", minute = "*", second = "0")

    public void myTimer() {
        System.out.println("Timer event: " + new Date());
    }

    // to employees
    private void checkInProgressTimesheetsOnLastDay() {
        // Weekly
        // Monthly
    }
    
    // to supervisors
    private void checkTimesheetSignedByEmployee() {
        
    }
    
    // to secretaries
    private void checkTimesheetSignedBySupervisor() {
        
    }
    
}
