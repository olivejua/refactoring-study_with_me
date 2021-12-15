package com.olivejua.study.utils;

public abstract class ErrorCodes {
    public static class Global {
        private static final String PREFIX = "G";
        public static final String CONSTRAINT_VIOLATION_EXCEPTION = PREFIX + "001";
    }

    public static class User {
        private static final String PREFIX = "U";
        public static final String NOT_FOUND_USER_EXCEPTION = PREFIX + "001";
    }

    public static class StudyRecruitment {
        private static final String PREFIX = "S";
        public static final String NOT_FOUND_STUDY_RECRUITMENT_EXCEPTION = PREFIX + "001";
    }

    public static class PlaceRecommendation {
        private static final String PREFIX = "P";
    }

    public static class Question {
        private static final String PREFIX = "Q";
    }
}
