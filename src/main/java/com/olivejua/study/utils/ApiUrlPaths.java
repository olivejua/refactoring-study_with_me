package com.olivejua.study.utils;

import java.net.URI;

public abstract class ApiUrlPaths {
	/***************************************************************************
	 * COMMON
	 **************************************************************************/
	private static final String SEP = "/";
	private static final String API_SEP = SEP + "api" + SEP;

	/***************************************************************************
	 * USERS
	 **************************************************************************/
	public static final String USERS = API_SEP + "users";

	public static class Users {
		private static final String COMMON = USERS + SEP;

		public static final String SIGN_UP = COMMON + "signup";
		public static final String NAME = COMMON + "name";

		public static URI createUser(Long id) {
			return URI.create(String.format("%s%d", USERS +SEP, id));
		}
	}

	/***************************************************************************
	 * BOARDS
	 **************************************************************************/
	public static final String STUDY_RECRUITMENT = API_SEP + "study-recruitment";
	public static final String QUESTION = API_SEP + "question";
	public static final String PLACES_RECOMMENDATION = API_SEP + "place-recommendation";

	/***************************************************************************
	 * POSTS
	 **************************************************************************/
	public static final String POSTS = SEP + "posts";
	public static final String VAR_POST_ID = SEP + "{postId}";

	/***************************************************************************
	 * COMMENTS
	 **************************************************************************/
	public static final String COMMENTS = SEP + "comments";
	public static final String REPLIES = SEP + "replies";
}
