package com.ecommerce.app.service.utils;

import com.github.slugify.Slugify;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SlugifyService {
    Slugify slugify = Slugify.builder().build();

    // Phương thức loại bỏ dấu tiếng Việt
    public String removeVietnameseAccents(String str) {
        if (str == null) return null;

        // Kiểm tra nếu chuỗi đã không có dấu tiếng Việt
        if (!containsVietnameseAccents(str)) {
            return str; // Nếu không có dấu, trả về nguyên chuỗi
        }

        String normalized = Normalizer.normalize(str, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(normalized).replaceAll("");
    }

    // Phương thức kiểm tra chuỗi có chứa dấu tiếng Việt không
    private boolean containsVietnameseAccents(String str) {
        // Kiểm tra chuỗi có chứa các ký tự dấu tiếng Việt hay không
        String regex = "[\\p{InCombiningDiacriticalMarks}]";
        return str != null && str.matches(".*" + regex + ".*");
    }

    // Phương thức tạo slug
    public String generateSlug(String title) {
        if (title == null || title.isEmpty()) {
            return "";
        }

        // Loại bỏ dấu tiếng Việt nếu cần thiết (chỉ khi có dấu)
        String titleWithoutAccents = removeVietnameseAccents(title);

        // Sử dụng Slugify để tạo slug từ title (có hoặc không có dấu)
        return slugify.slugify(titleWithoutAccents);
    }
}
