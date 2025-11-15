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
    protected ApplicationRunner initData(AccountRepository accountRepository,
                                         ActionRepository actionRepository,
                                         TopicRepository topicRepository,
                                         PackageRepository packageRepository,
                                         PasswordEncoder passwordEncoder) {
        return args -> {
            // ===== Accounts mặc định =====
            if (accountRepository.count() == 0) {
                // Admin
                Account admin = new Account(
                        "admin@gmail.com",
                        passwordEncoder.encode("123456"),
                        "admin",
                        Role.ADMIN,
                        100,
                        LocalDateTime.now(),
                        LocalDateTime.now(),
                        true
                );
                accountRepository.save(admin);

                // Một số user demo
                Account user1 = new Account(
                        "alice@example.com",
                        passwordEncoder.encode("123456"),
                        "alice",
                        Role.USER,
                        50,
                        LocalDateTime.now(),
                        LocalDateTime.now(),
                        true
                );
                accountRepository.save(user1);

                Account user2 = new Account(
                        "bob@example.com",
                        passwordEncoder.encode("123456"),
                        "bob",
                        Role.USER,
                        30,
                        LocalDateTime.now(),
                        LocalDateTime.now(),
                        true
                );
                accountRepository.save(user2);

                Account user3 = new Account(
                        "charlie@example.com",
                        passwordEncoder.encode("123456"),
                        "charlie",
                        Role.USER,
                        75,
                        LocalDateTime.now(),
                        LocalDateTime.now(),
                        true
                );
                accountRepository.save(user3);

                Account user4 = new Account(
                        "daisy@example.com",
                        passwordEncoder.encode("123456"),
                        "daisy",
                        Role.USER,
                        20,
                        LocalDateTime.now(),
                        LocalDateTime.now(),
                        true
                );
                accountRepository.save(user4);

                Account user5 = new Account(
                        "eric@example.com",
                        passwordEncoder.encode("123456"),
                        "eric",
                        Role.USER,
                        10,
                        LocalDateTime.now(),
                        LocalDateTime.now(),
                        true
                );
                accountRepository.save(user5);

                Account user6 = new Account(
                        "fiona@example.com",
                        passwordEncoder.encode("123456"),
                        "fiona",
                        Role.USER,
                        120,
                        LocalDateTime.now(),
                        LocalDateTime.now(),
                        true
                );
                accountRepository.save(user6);

                Account user7 = new Account(
                        "george@example.com",
                        passwordEncoder.encode("123456"),
                        "george",
                        Role.USER,
                        5,
                        LocalDateTime.now(),
                        LocalDateTime.now(),
                        true
                );
                accountRepository.save(user7);

                Account user8 = new Account(
                        "hannah@example.com",
                        passwordEncoder.encode("123456"),
                        "hannah",
                        Role.USER,
                        90,
                        LocalDateTime.now(),
                        LocalDateTime.now(),
                        true
                );
                accountRepository.save(user8);

                Account user9 = new Account(
                        "ivan@example.com",
                        passwordEncoder.encode("123456"),
                        "ivan",
                        Role.USER,
                        60,
                        LocalDateTime.now(),
                        LocalDateTime.now(),
                        true
                );
                accountRepository.save(user9);

                Account user10 = new Account(
                        "julia@example.com",
                        passwordEncoder.encode("123456"),
                        "julia",
                        Role.USER,
                        40,
                        LocalDateTime.now(),
                        LocalDateTime.now(),
                        true
                );
                accountRepository.save(user10);
            }

            // ===== Action mặc định =====
            if (actionRepository.count() == 0) {
                Action createRoom = new Action("Create new room", 10);
                actionRepository.save(createRoom);
                Action joinRoom = new Action("Join room", 5);
                actionRepository.save(joinRoom);
            }

            // ===== Topic mặc định =====
            if (topicRepository.count() == 0) {

                // Hai topic EXE cũ
                Topic exe101 = new Topic(
                        "EXE101",
                        "Students in Experimental Entrepreneurship 1 will learn to generate and validate startup ideas via customer discovery. The course offers optional lectures and workshops instead of mandatory content to help students gain experience and find customers.",
                        true
                );
                topicRepository.save(exe101);

                Topic exe201 = new Topic(
                        "EXE201",
                        "This course teaches students to implement and sell the products/services from their startup ideas. While it has no mandatory content, it offers optional lectures, talks, and workshops to help students gain experience and find real customers.",
                        true
                );
                topicRepository.save(exe201);

                // ===== Các topic mới từ list =====
                topicRepository.save(new Topic("TRS601", "English 6 (University success)", true));
                topicRepository.save(new Topic("TMI101", "Traditional musical instrument", true));
                topicRepository.save(new Topic("OTP101", "Orientation and General Training Program", true));
                topicRepository.save(new Topic("CSI104", "Introduction to computing", true));
                topicRepository.save(new Topic("SSL101c", "Academic Skills for University Success", true));
                topicRepository.save(new Topic("PRF192", "Programming Fundamentals", true));
                topicRepository.save(new Topic("MAE101", "Mathematics for Engineering", true));
                topicRepository.save(new Topic("CEA201", "Computer Organization and Architecture", true));
                topicRepository.save(new Topic("PRO192", "Object-Oriented Programming", true));
                topicRepository.save(new Topic("MAD101", "Discrete mathematics", true));
                topicRepository.save(new Topic("OSG202", "Operating Systems", true));
                topicRepository.save(new Topic("NWC203c", "Computer Networking", true));
                topicRepository.save(new Topic("SSG104", "Communication and In-Group Working Skills", true));
                topicRepository.save(new Topic("JPD113", "Elementary Japanese 1- A1.1", true));
                topicRepository.save(new Topic("WED201c", "Web Design", true));
                topicRepository.save(new Topic("CSD201", "Data Structures and Algorithms", true));
                topicRepository.save(new Topic("DBI202", "Database Systems", true));
                topicRepository.save(new Topic("LAB211", "OOP with Java Lab", true));
                topicRepository.save(new Topic("MAS291", "Statistics & Probability", true));
                topicRepository.save(new Topic("SWE201c", "Introduction to Software Engineering", true));
                topicRepository.save(new Topic("JPD123", "Elementary Japanese 1-A1.2", true));
                topicRepository.save(new Topic("IOT102", "Internet of Things", true));
                topicRepository.save(new Topic("PRJ301", "Java Web application development", true));
                topicRepository.save(new Topic("SWP391", "Software development project", true));
                topicRepository.save(new Topic("ITE302c", "Ethics in IT", true));
                topicRepository.save(new Topic("ACC101", "Principles of Accounting", true));
                topicRepository.save(new Topic("SWR302", "Software Requirements", true));
                topicRepository.save(new Topic("SWT301", "Software Testing", true));
                topicRepository.save(new Topic("OJT202", "On the job training", true));
                topicRepository.save(new Topic("ENW493c", "Research Methods & Academic Writing Skills", true));
                topicRepository.save(new Topic("EXE101-FU", "Experiential Entrepreneurship 1", true));
                topicRepository.save(new Topic("SAP311", "SAP General 1", true));
                topicRepository.save(new Topic("SAP321", "SAP General 2", true));
                topicRepository.save(new Topic("PMG201c", "Project Management", true));
                topicRepository.save(new Topic("SWD392", "Software Architecture and Design", true));
                topicRepository.save(new Topic("MLN122", "Political economics of Marxism – Leninism", true));
                topicRepository.save(new Topic("WDU203c", "The UI/UX Design", true));
                topicRepository.save(new Topic("PRM392", "Mobile Programming", true));
                topicRepository.save(new Topic("MLN111", "Philosophy of Marxism – Leninism", true));
                topicRepository.save(new Topic("SAP341", "SAP Application Development with ABAP", true));
                topicRepository.save(new Topic("EXE201-FU", "Experiential Entrepreneurship 2", true));
                topicRepository.save(new Topic("SAP490", "SAP Interdisciplinary Capstone Project", true));
                topicRepository.save(new Topic("MLN131", "Scientific socialism", true));
                topicRepository.save(new Topic("VNR202", "History of Vietnam Communist Party", true));
                topicRepository.save(new Topic("HCM202", "Ho Chi Minh Ideology", true));
            }

            // ===== Package mặc định =====
            if (packageRepository.count() == 0) {
                packageRepository.save(new Package(
                        null,
                        "Free",
                        "Gói miễn phí với 10 token",
                        0,
                        0
                ));
                packageRepository.save(new Package(
                        null,
                        "VIP",
                        "Gói VIP với 100 token",
                        100,
                        20000
                ));
                packageRepository.save(new Package(
                        null,
                        "VIP PRO",
                        "Gói VIP Pro với 350 token",
                        350,
                        50000
                ));
            }
        };
    }
}
