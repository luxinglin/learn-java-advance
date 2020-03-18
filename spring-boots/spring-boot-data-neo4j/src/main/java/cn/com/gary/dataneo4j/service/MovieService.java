package cn.com.gary.dataneo4j.service;

import cn.com.gary.dataneo4j.model.ActedIn;
import cn.com.gary.dataneo4j.model.Directed;
import cn.com.gary.dataneo4j.model.Movie;
import cn.com.gary.dataneo4j.model.Person;

/**
 * movie service
 */
public interface MovieService {
    /**
     * add person
     *
     * @param person
     * @return
     */
    Person addPerson(Person person);

    /**
     * find person
     *
     * @param id
     * @return
     */
    Person findOnePerson(long id);

    /**
     * delete person
     *
     * @param id
     */
    void deleteOnePerson(long id);

    /**
     * add movie
     *
     * @param movie
     * @return
     */
    Movie addMovie(Movie movie);

    /**
     * find movie
     *
     * @param id
     * @return
     */
    Movie findOneMovie(long id);

    /**
     * directed
     *
     * @param directed
     * @return
     */
    Directed directed(Directed directed);

    /**
     * act in
     *
     * @param actedIn
     * @return
     */
    ActedIn actedIn(ActedIn actedIn);
}