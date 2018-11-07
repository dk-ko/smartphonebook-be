package com.soda.phonebook.domain;

import com.soda.phonebook.domain.BaseEntity;

// Mock Entity for test id generation
public class MockEntity extends BaseEntity{
	public static <T extends BaseEntity> T mock(Class<T> c, Long id) {
		try {
            T mock = c.newInstance();
            mock.id = id;
            return mock;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
	}
}
