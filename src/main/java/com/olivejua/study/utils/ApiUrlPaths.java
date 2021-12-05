package com.olivejua.study.utils;

import java.net.URI;

public abstract class ApiUrlPaths {
	private static final String SEP = "/";

	public static final String USERS = SEP + "users";

	public static final String POSTS = SEP + "posts";
	public static final String STUDY = POSTS + SEP + "study";
	public static final String QUESTIONS = POSTS + SEP + "questions";
	public static final String PLACES = POSTS + SEP + "places";

	public static final String COMMENTS = SEP + "comments";
	public static final String REPLIES = SEP + "replies";

	public static class Users {
		private static final String COMMON = USERS + SEP;

		public static final String SIGN_UP = COMMON + "signup";
		public static final String NAME = COMMON + "name";
		public static final String NAME_LIST = COMMON + "list";

		public static URI createUser(Long id) {
			return URI.create(String.format("%s%d", USERS +SEP, id));
		}
	}

	public static class Study {
		private static final String COMMON = STUDY + SEP;

		public static final String LIST = COMMON + "list";
	}

	public static class Questions {
		private static final String COMMON = QUESTIONS + SEP;

		public static  final String LIST = COMMON + "list";
	}

	public static class Places {
		private static final String COMMON = PLACES + SEP;

		public static final String LIST = COMMON + "list";
	}
}
