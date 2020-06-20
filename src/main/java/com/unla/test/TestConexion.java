package com.unla.test;

import com.mongodb.client.MongoDatabase;
import com.unla.dao.MongoUtil;

public class TestConexion {
	static MongoDatabase db = null;
	public static void main(String[] args) {
		db =  MongoUtil.getDatabase();
	}
}
