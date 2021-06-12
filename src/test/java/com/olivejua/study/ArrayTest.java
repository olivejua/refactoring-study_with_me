package com.olivejua.study;

import com.olivejua.study.domain.Role;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ArrayTest {

    @Test
    public void whenTestingForEquality_ShouldBeEqual() throws Exception {
        List<String> list1 = Arrays.asList("1", "2", "3", "4");
        List<String> list2 = Arrays.asList("1", "2", "3", "4");
        List<String> list3 = Arrays.asList("1", "2", "4", "3");

        assertThat(list1)
                .isEqualTo(list2)
                .isNotEqualTo(list3);

        assertThat(list1.equals(list2)).isTrue();
        assertThat(list1.equals(list3)).isFalse();
    }

    @Test
    public void test() {
        Role role = Role.GUEST;
        System.out.println(role.name());
    }
}
