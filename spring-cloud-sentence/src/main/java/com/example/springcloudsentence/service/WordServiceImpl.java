package com.example.springcloudsentence.service;

import com.example.springcloudsentence.dao.AdjectiveClient;
import com.example.springcloudsentence.dao.ArticleClient;
import com.example.springcloudsentence.dao.NounClient;
import com.example.springcloudsentence.dao.SubjectClient;
import com.example.springcloudsentence.dao.VerbClient;
import com.example.springcloudsentence.domain.Sentence.Role;
import java.util.concurrent.Executor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * @author Ken Krueger, Jorge Centeno Fernandez
 */
@Service
public class WordServiceImpl implements WordService {

    @Autowired
    VerbClient verbClient;
    @Autowired
    SubjectClient subjectClient;
    @Autowired
    ArticleClient articleClient;
    @Autowired
    AdjectiveClient adjectiveClient;
    @Autowired
    NounClient nounClient;
    @Autowired
    Executor executor;	//	Source of threads

    @Override
    @HystrixCommand(fallbackMethod = "getFallbackSubject")
    public Observable<Object[]> getSubject() {
        //	This 'reactive' observable is backed by a regular Java Callable, which can run in a different thread:
        return Observable.fromCallable(
                () -> new Object[]{Role.subject, subjectClient.getWord()}
        ).subscribeOn(Schedulers.from(executor));
    }

    @Override
    @HystrixCommand(fallbackMethod = "getFallbackVerb")
    public Observable<Object[]> getVerb() {
        return Observable.fromCallable(
                () -> new Object[]{Role.verb, verbClient.getWord()}
        ).subscribeOn(Schedulers.from(executor));
    }

    @Override
    @HystrixCommand(fallbackMethod = "getFallbackArticle")
    public Observable<Object[]> getArticle() {
        return Observable.fromCallable(
                () -> new Object[]{Role.article, articleClient.getWord()}
        ).subscribeOn(Schedulers.from(executor));
    }

    @Override
    @HystrixCommand(fallbackMethod = "getFallbackAdjective")
    public Observable<Object[]> getAdjective() {
        return Observable.fromCallable(
                () -> new Object[]{Role.adjective, adjectiveClient.getWord()}
        ).subscribeOn(Schedulers.from(executor));
    }

    @Override
    @HystrixCommand(fallbackMethod = "getFallbackNoun",
            commandProperties = {
                @HystrixProperty(
                        name = "circuitBreaker.errorThresholdPercentage", value = "5"
                )
            })
    public Observable<Object[]> getNoun() {
        return Observable.fromCallable(
                () -> new Object[]{Role.noun, nounClient.getWord()}
        ).subscribeOn(Schedulers.from(executor));
    }

    public Object[] getFallbackSubject() {
        return new Object[]{Role.subject, "Someone"};
    }

    public Object[] getFallbackVerb() {
        return new Object[]{Role.verb, "does"};
    }

    public Object[] getFallbackArticle() {
        return new Object[]{Role.article, ""};
    }

    public Object[] getFallbackAdjective() {
        return new Object[]{Role.adjective, ""};
    }

    public Object[] getFallbackNoun() {
        return new Object[]{Role.noun, "something"};
    }

}
