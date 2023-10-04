package uk.co.topfieldconsultancy.stem.infrastructure.web;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class ErrorResponse {
    private boolean success = false;
    private String error;
    private String error_message;
}
