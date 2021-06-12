package com.olivejua.study;

import com.olivejua.study.config.ImageConfig;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ConstraintTest {

    @Autowired
    private ImageConfig imageConfig;

    @Test
    void test() {
        String path = imageConfig.getDefaultPath();

        System.out.println("The image path is " + path);
        Assertions.assertThat(path).isEqualTo("C:\\Users\\account1\\test\\project-study-image");
    }
}
