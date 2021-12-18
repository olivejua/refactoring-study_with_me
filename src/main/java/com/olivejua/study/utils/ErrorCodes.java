package com.olivejua.study.utils;

public abstract class ErrorCodes {
    public static class Global {
        private static final String PREFIX = "GB";
        public static final String CONSTRAINT_VIOLATION_EXCEPTION = PREFIX + "001";
    }

    public static class User {
        private static final String PREFIX = "US";
        public static final String NOT_FOUND_USER_EXCEPTION = PREFIX + "001";
    }

    public static class Post {
        private static final String PREFIX = "PT";
        public static final String NOT_FOUND_POST_EXCEPTION = PREFIX + "001";
        public static final String DIFFERENT_USER_WITH_POST_AUTHOR_EXCEPTION = PREFIX + "002";
    }

    public static class StudyRecruitment {
        private static final String PREFIX = "SR";

    }

    public static class PlaceRecommendation {
        private static final String PREFIX = "PR";
    }

    public static class Question {
        private static final String PREFIX = "QT";
    }
}
