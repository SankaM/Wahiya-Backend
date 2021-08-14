package com.monda.edoctor.wahiya.dto.req;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.UUID;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AttachmentDetails {

    private UUID attachmentId;
    private String attachmentName;
    private String attachmentKey;
}
