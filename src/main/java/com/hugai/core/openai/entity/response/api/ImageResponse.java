package com.hugai.core.openai.entity.response.api;

import com.hugai.core.openai.entity.response.ApiResponse;
import com.theokanning.openai.image.Image;
import lombok.*;

import java.util.List;

/**
 * @author WuHao
 * @since 2023/6/1 15:35
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ImageResponse extends ApiResponse {

    Long created;

    List<Image> data;

}
