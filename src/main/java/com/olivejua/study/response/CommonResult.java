package com.olivejua.study.response;

import lombok.Getter;

@Getter
public abstract class CommonResult {
    private boolean success;

    protected void success() {
        success = true;
    }

    protected void fail() {
        success = false;
    }
}
