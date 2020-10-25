package io.falcon.assignment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MessageDTO {

    @JsonProperty("content")
    private String content;
    @JsonProperty("timestamp")
    private String timestamp;
    @JsonProperty("longest_palindrome_size")
    private int longestPalindrome;
}
