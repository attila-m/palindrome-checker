package io.falcon.assignment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
public class MessageDTO {
    @NotBlank(message = "Content is mandatory")
    @JsonProperty("content")
    private String content;
    @NotBlank(message = "Timestamp is mandatory")
    @JsonProperty("timestamp")
    private String timestamp;
    @JsonProperty("longest_palindrome_size")
    private int longestPalindrome;
}
