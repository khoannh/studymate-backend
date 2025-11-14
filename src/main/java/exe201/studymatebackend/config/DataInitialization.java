package exe201.studymatebackend.config;

import exe201.studymatebackend.enums.Role;
import exe201.studymatebackend.pojo.Account;
import exe201.studymatebackend.pojo.Action;
import exe201.studymatebackend.pojo.Package;
import exe201.studymatebackend.pojo.Topic;
import exe201.studymatebackend.repository.AccountRepository;
import exe201.studymatebackend.repository.ActionRepository;
import exe201.studymatebackend.repository.PackageRepository;
import exe201.studymatebackend.repository.TopicRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@Configuration
public class DataInitialization {


    @Bean
    protected ApplicationRunner initData(AccountRepository accountRepository, ActionRepository actionRepository, TopicRepository topicRepository, PackageRepository packageRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (accountRepository.count() == 0) {
                Account account = new Account("admin@gmail.com", passwordEncoder.encode("123456"), "admin", Role.ADMIN, 100, LocalDateTime.now(), LocalDateTime.now(), true);
                accountRepository.save(account);
            }
            if (actionRepository.count() == 0) {
                Action createRoom = new Action("Create new room", 10);
                actionRepository.save(createRoom);
                Action joinRoom = new Action("Join room", 5);
                actionRepository.save(joinRoom);
            }
            if (topicRepository.count() == 0) {

                Topic exe101 = new Topic("EXE101", "Students in Experimental Entrepreneurship 1 will learn to generate and validate startup ideas via customer discovery. The course offers optional lectures and workshops instead of mandatory content to help students gain experience and find customers.", true);
                topicRepository.save(exe101);
                Topic exe201 = new Topic("EXE201", "This course teaches students to implement and sell the products/services from their startup ideas. While it has no mandatory content, it offers optional lectures, talks, and workshops to help students gain experience and find real customers.", true);
                topicRepository.save(exe201);
            }
            if (packageRepository.count() == 0) {
                packageRepository.save(new Package(null, "Free",
                        "Gói miễn phí với 10 token", 0, 0));
                packageRepository.save(new Package(null, "VIP",
                        "Gói VIP với 100 token", 100, 20000));
                packageRepository.save(new Package(null, "VIP PRO",
                        "Gói VIP Pro với 350 token", 350, 50000));
            }


        };
    }

}
