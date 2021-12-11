package com.olivejua.study.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@ToString
public class CommonResult {
    private boolean success; // 성공여부는
    private String code;
    private String msg;
    private HttpStatus httpStatus = HttpStatus.OK; // 응답 코드에 전달하면 됨
}
