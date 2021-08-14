package com.monda.edoctor.wahiya.dto.res;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.monda.edoctor.wahiya.dto.req.AttachmentDetails;
import lombok.*;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AttachmentRes {

    private String attachmentKey;
    private ByteArrayOutputStream downloadInputStream;
}
