package com.example.springcloudsentence.service;

import rx.Observable;

public interface WordService {

	Observable<Object[]> getSubject();
	Observable<Object[]> getVerb();
	Observable<Object[]> getArticle();
	Observable<Object[]> getAdjective();
	Observable<Object[]> getNoun();	

}
