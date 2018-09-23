/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timers;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.jms.Session;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import jee18.dao.TimesheetAccess;
import jee18.entities.TimesheetEntity;
import jee18.entities.enums.TimesheetStatus;

/**
 *
 * @author okaracalik
 */
@Startup
public class TaskManager {

    @EJB
    private TimesheetAccess timesheetAccess;

    @Resource(lookup = "mail/uniko-mail")
    private Session mailSession;

    public Session getMailSession() {
        return mailSession;
    }

    public void setMailSession(Session mailSession) {
        this.mailSession = mailSession;
    }

    private Map<String, List<String>> reminders = new HashMap<>();

    // to employees
    @Schedule(dayOfWeek = "Sun", hour = "1")
    public void checkTimesheetInProgressWeekly() {
        List<TimesheetEntity> ts = timesheetAccess.getTimesheetListByEndDateAndStatus(LocalDate.now(), TimesheetStatus.IN_PROGRESS);
        List<String> emails = getEmployeeEmails(ts);
        emails.forEach(e -> {
            reminders.put(
                    e,
                    Stream.concat(reminders.get(e).stream(), Arrays.asList("You have timesheet in progress that must be signed as employee.").stream()).collect(Collectors.toList())
            );
        });
    }

    // to employees
    @Schedule(dayOfMonth = "Last", hour = "1")
    public void checkTimesheetInProgressMonthly() {
        List<TimesheetEntity> ts = timesheetAccess.getTimesheetListByEndDateAndStatus(LocalDate.now(), TimesheetStatus.IN_PROGRESS);
        List<String> emails = getEmployeeEmails(ts);
        emails.forEach(e -> {
            reminders.put(
                    e,
                    Stream.concat(reminders.get(e).stream(), Arrays.asList("You have timesheet in progress that must be signed as employee.").stream()).collect(Collectors.toList())
            );
        });
    }

    // to supervisors + assistants
    @Schedule(dayOfWeek = "*", hour = "1")
    public void checkTimesheetSignedByEmployee() {
        List<TimesheetEntity> ts = timesheetAccess.getTimesheetListByStatus(TimesheetStatus.SIGNED_BY_EMPLOYEE);
        List<String> emails = getSupervisorEmails(ts);
        emails.addAll(getAssistantsEmails(ts));
        emails.forEach(e -> {
            reminders.put(
                    e,
                    Stream.concat(reminders.get(e).stream(), Arrays.asList("You have timesheet signed by employee, and you should check.").stream()).collect(Collectors.toList())
            );
        });
    }

    // to secretaries
    @Schedule(dayOfWeek = "*", hour = "1")
    public void checkTimesheetSignedBySupervisor() {
        List<TimesheetEntity> ts = timesheetAccess.getTimesheetListByStatus(TimesheetStatus.SIGNED_BY_SUPERVISOR);
        List<String> emails = getSecretaryEmails(ts);
        emails.forEach(e -> {
            reminders.put(
                    e,
                    Stream.concat(reminders.get(e).stream(), Arrays.asList("You have timesheet signed by supervisor, and you should check.").stream()).collect(Collectors.toList())
            );
        });
    }

    @Schedule(dayOfWeek = "*", hour = "7")
    public void sendEmails() throws MessagingException {
        reminders.forEach((email, messages) -> {
            try {
                Message msg = new MimeMessage((MimeMessage) mailSession);
                msg.setSubject("Reminder");
                msg.setSentDate(new Date());
                msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email, false));
                msg.setText(String.join(". ", messages));
                Transport.send(msg);
            }
            catch (MessagingException ex) {
                Logger.getLogger(TaskManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    private List<String> getEmployeeEmails(List<TimesheetEntity> timesheets) {
        return timesheets
                .stream().map(x -> x.getContract()).collect(Collectors.toList())
                .stream().map(x -> x.getEmployee()).collect(Collectors.toList())
                .stream().map(x -> x.getPerson().getEmailAddress()).collect(Collectors.toList());
    }

    private List<String> getSupervisorEmails(List<TimesheetEntity> timesheets) {
        return timesheets
                .stream().map(x -> x.getContract()).collect(Collectors.toList())
                .stream().map(x -> x.getSupervisor()).collect(Collectors.toList())
                .stream().map(x -> x.getPerson().getEmailAddress()).collect(Collectors.toList());
    }

    private List<String> getSecretaryEmails(List<TimesheetEntity> timesheets) {
        return timesheets
                .stream().map(x -> x.getContract()).collect(Collectors.toList())
                .stream().flatMap(x -> x.getSecretaries().stream()).collect(Collectors.toList())
                .stream().map(x -> x.getPerson().getEmailAddress()).collect(Collectors.toList());
    }

    private List<String> getAssistantsEmails(List<TimesheetEntity> timesheets) {
        return timesheets
                .stream().map(x -> x.getContract()).collect(Collectors.toList())
                .stream().flatMap(x -> x.getAssistants().stream()).collect(Collectors.toList())
                .stream().map(x -> x.getPerson().getEmailAddress()).collect(Collectors.toList());
    }

}
