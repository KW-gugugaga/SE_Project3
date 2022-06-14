package conpanda9.shop.service;

import conpanda9.shop.DTO.NoticeDTO;
import conpanda9.shop.domain.*;
import conpanda9.shop.repository.AdminRepository;
import conpanda9.shop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdminService {

    private final AdminRepository adminRepository;

    public List<Question> findAllQuestion() {
        return adminRepository.findAllQuestion();
    }

    public Question findQuestion(Long id) {
        return adminRepository.findQuestion(id);
    }

    public void addAnswer(Long id, String answer) {
        adminRepository.addAnswer(id, answer);
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

    public void editAnswer(Long id, String answer) {
        adminRepository.editAnswer(id, answer);
    }

    public List<User> findAllUser(){
        return adminRepository.findAllUser();
    }
    public User findUser(Long id) {
        return adminRepository.findUser(id);
    }

    @Transactional
    public void setNullUserActHistory(Long id) {

        log.info("delete user id={}", id);
        Optional<Seller> seller = adminRepository.findSellerByUserId(id);
        seller.ifPresent(value -> value.setUser(null));

        List<Shared> sharedList = adminRepository.findSharedByUserId(id);
        if(!sharedList.isEmpty()) {
            for (Shared shared : sharedList) {
                shared.setUser(null);
            }
        }

        List<Sold> soldList = adminRepository.findSoldByUserId(id);
        if(!soldList.isEmpty()) {
            for (Sold sold : soldList) {
                sold.setUser(null);
            }
        }

        List<Report> reportList = adminRepository.findReportByUserId(id);
        if(!reportList.isEmpty()) {
            for (Report report : reportList) {
                report.setUser(null);
            }
        }
    }

    public void deleteUser(Long id) {
        adminRepository.deleteUser(id);
    }

    public Report findReport(Long id) {
        return adminRepository.findReport(id);
    }

    public List<Gifticon> findAllGitficons() {
        return adminRepository.findAllGifticons();
    }

    public void setNullGifticon(Long id) {
        // seller가 팔고있는 상품 목록에서 gifticon 삭제
        Gifticon gifticon = adminRepository.findGifticon(id);
        gifticon.getSeller().getGifticonList().remove(gifticon);   // seller 리스트에서 기프티콘 목록 삭제
        gifticon.getBrand().getGifticonList().remove(gifticon);   // brand 리스트에서 기프티콘 목록 삭제
        gifticon.getCategory().getGitficonList().remove(gifticon);   // category 리스트에서 기프티콘 목록 삭제
        adminRepository.deleteGifticon(id);
    }

    public void saveReport(Alarm alarm) {
        adminRepository.saveAlarm(alarm);
    }

    public void updateReportComplete(Long id) {
        adminRepository.updateReportComplete(id);
    }
}
