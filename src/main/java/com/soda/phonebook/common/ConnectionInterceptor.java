//package com.soda.phonebook.common;
//
//import java.sql.Connection;
//import java.sql.SQLException;
//
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.hibernate.Session;
//import org.hibernate.jdbc.Work;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//import lombok.extern.slf4j.Slf4j;
//
// 추후 참고 
//@Slf4j
//@Aspect
//@Component
//public class ConnectionInterceptor {
//
//	@PersistenceContext
//	private EntityManager entityManager;
//	private boolean autoCommit;
//	private boolean readOnly;
//
//	@Around(value = "@annotation(transactional)", argNames = "transactional")
//	public Object proceed(ProceedingJoinPoint pjp, Transactional transactional) throws Throwable {
//		if (!transactional.readOnly()) {
//			return pjp.proceed();
//		} else {
//			log.debug("읽기전용 작업을 수행하기 위해 현 connection를 readonly로 설정합니다");
//			Session session = entityManager.unwrap(Session.class);
//			try {
//				session.doWork(new Work() {
//					@Override
//					public void execute(Connection connection) throws SQLException {
//						autoCommit = connection.getAutoCommit();
//						readOnly = connection.isReadOnly();
//						// MySQL SLAVE 서버에 접속하기 위해 Connection 속성을 설정합니다.
//						connection.setAutoCommit(false);
//						connection.setReadOnly(true);
//					}
//				});
//				// @ReadOnlyConnection이 선언된 메소드를 실행합니다.
//				return pjp.proceed();
//			} finally {
//				session.doWork(new Work() {
//					@Override
//					public void execute(Connection connection) throws SQLException {
//						connection.setAutoCommit(autoCommit);
//						connection.setReadOnly(readOnly);
//					}
//				});
//				log.debug("읽기전용 작업을 수행하고, connection의 원래 설정으로 재설정했습니다");
//			}
//		}
//	}
//}
