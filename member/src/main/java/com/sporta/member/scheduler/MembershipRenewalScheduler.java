package com.sporta.member.scheduler;

import com.sporta.member.model.Member;
import com.sporta.member.repository.MemberRepository;
import com.sporta.member.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Component
public class MembershipRenewalScheduler {

    private final EmailService emailService;
    
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    public MembershipRenewalScheduler(EmailService emailService) {
        this.emailService = emailService;
    }

    @Scheduled(cron = "0 0 9 * * *") // Run daily at 9 AM
    //@Scheduled(cron = "0 * * * * *") // Run every minute
    public void sendMonthlyReminders() {
        List<Member> members = memberRepository.findAll();
        LocalDate today = LocalDate.now();

        for (Member member : members) {
            LocalDate joinDate = member.getJoinDate();

            // Check if today is the renewal day for the member
            if (isRenewalDue(joinDate, today)) {
                String subject = "Membership Renewal";
                String message = "Dear " + member.getName() + ",\n\nThis is a reminder to renew your badminton membership.";
                emailService.sendEmail(member.getEmail(), subject, message);
            }
        }
    }

    private boolean isRenewalDue(LocalDate joinDate, LocalDate today) {
        // Check if the day of month of the join date matches today’s day of the month
        return joinDate.getDayOfMonth() == today.getDayOfMonth() &&
                ChronoUnit.MONTHS.between(joinDate, today) > 0;
    }
}
