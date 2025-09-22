package exe201.studymatebackend.config;

import exe201.studymatebackend.enums.Role;
import exe201.studymatebackend.pojo.Account;
import exe201.studymatebackend.pojo.Action;
import exe201.studymatebackend.repository.AccountRepository;
import exe201.studymatebackend.repository.ActionRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@Configuration
public class DataInitialization {


    @Bean
    protected ApplicationRunner initData(AccountRepository accountRepository, ActionRepository actionRepository, PasswordEncoder passwordEncoder) {
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


        };
    }

}
