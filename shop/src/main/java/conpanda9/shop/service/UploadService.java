package conpanda9.shop.service;

import conpanda9.shop.DTO.NoticeDTO;
import conpanda9.shop.DTO.UploadDTO;
import conpanda9.shop.domain.*;
import conpanda9.shop.repository.QuestionRepository;
import conpanda9.shop.repository.UploadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UploadService {

    private final UploadRepository uploadRepository;

    public void saveGifticon(Gifticon gifticon) {
        uploadRepository.saveGifticon(gifticon);
    }

}
