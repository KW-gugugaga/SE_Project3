package conpanda9.shop.service;

import conpanda9.shop.DTO.NoticeDTO;
import conpanda9.shop.domain.Notice;
import conpanda9.shop.domain.Question;
import conpanda9.shop.domain.Report;
import conpanda9.shop.repository.AdminRepository;
import conpanda9.shop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    public Notice findNotice(Long id) {
        return adminRepository.findNotice(id);
    }

    @Transactional
    public void updateNotice(Long id, NoticeDTO updateDTO, boolean important) {
        Notice notice = adminRepository.findNotice(id);
        notice.setTitle(updateDTO.getTitle());
        notice.setContents(updateDTO.getContents());
        notice.setImportant(important);   // 중요 공지 여부
        notice.setLastModifiedDate(LocalDateTime.now());   // 마지막 수정 시간 최신으로 update
    }

    public void deleteNotice(Long id) {
        adminRepository.deleteNotice(id);
    }

    public void addNotice(NoticeDTO noticeDTO, boolean important) {
        Notice notice = new Notice(noticeDTO.getTitle(), noticeDTO.getContents(), LocalDateTime.now(), LocalDateTime.now(), important);
        adminRepository.saveNotice(notice);
    }

    public void addNoticeCount(Long id) {
        adminRepository.addNoticeCount(id);
    }
}
