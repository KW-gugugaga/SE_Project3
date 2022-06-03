package conpanda9.shop.service;

import conpanda9.shop.DTO.LoginDTO;
import conpanda9.shop.domain.Notice;
import conpanda9.shop.domain.Question;
import conpanda9.shop.domain.Report;
import conpanda9.shop.domain.User;
import conpanda9.shop.repository.AdminRepository;
import conpanda9.shop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;
    private final AdminRepository adminRepository;

    public List<Question> findAllQuestion() {
        return adminRepository.findAllQuestion();
    }

    public List<Report> findAllReport() {
        return adminRepository.findAllReport();
    }

    public List<Notice> findAllNotice() {
        List<Notice> notices = new ArrayList<>(adminRepository.findAllImportantNotice());
        notices.addAll(adminRepository.findAllNormalNotice());
        return notices;
    }
}
