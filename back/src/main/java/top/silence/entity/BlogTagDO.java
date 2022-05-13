package top.silence.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 博客Id与标签Id的映射关系
 * @author Silence
 * @date 2022/5/13 9:28
 */
@TableName("blog_tag")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogTagDO {

    /**
     * 博客Id
     */
    private Long blogId;

    /**
     * 标签Id
     */
    private Long tagId;

}
