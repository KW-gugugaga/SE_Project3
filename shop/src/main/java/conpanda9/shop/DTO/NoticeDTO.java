package conpanda9.shop.DTO;

import lombok.Data;

@Data
public class NoticeDTO {

    private String title;
    private String contents;
    private boolean important;

    public boolean isImportant() {
        return important;
    }
}
