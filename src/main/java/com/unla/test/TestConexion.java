package com.unla.test;

import com.mongodb.client.MongoDatabase;
import com.unla.dao.MongoUtil;

public class TestConexion {
	MongoDatabase db = MongoUtil.getDatabase();
}
