package top.silence.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 归档页面博客信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArchiveDTO {
    private Long id;
    private String title;
    private String day;
}
